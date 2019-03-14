package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class Camera {
	
	private float distanceFromPlayer = 235;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 30;
	private float yaw = 0;
	private float roll;
	private Terrain terrain;
	
	
	private Entity player;
	
	public Camera(Terrain ter){
		terrain = ter;
		float pos = terrain.getSize();
		this.player = new Entity(null, new Vector3f(pos/2, 0, pos/2), 0, 180, 0, 1f);
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		moveTerrain();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		yaw%=360;
	}
	
	public void invertPitch(){
		this.pitch = -pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 4;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch+4)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch+4)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.03f;
		distanceFromPlayer -= zoomLevel;
		if(distanceFromPlayer<5){
			distanceFromPlayer = 5;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.2f;
			pitch -= pitchChange;
			if(pitch < 0){
				pitch = 0;
			}else if(pitch > 90){
				pitch = 90;
			}
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private void moveTerrain() {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (5 * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (5 * Math.cos(Math.toRadians(theta)));
		float offsetX1 = (float) (5 * Math.sin(Math.toRadians((theta + 90)))%360);
		float offsetZ1 = (float) (5 * Math.cos(Math.toRadians((theta + 90)))%360);
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			terrain.setKretanjeXZ(offsetX, offsetZ);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			terrain.setKretanjeXZ(-offsetX, -offsetZ);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			terrain.setKretanjeXZ(offsetX1, offsetZ1);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			terrain.setKretanjeXZ(-offsetX1, -offsetZ1);
		}
	}
}