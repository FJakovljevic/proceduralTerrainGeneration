package terrains;

import java.util.Random;

import org.lwjgl.input.Keyboard;

public class HeightGenerator {

	private static float AMPLITUDE = 50f;  //visina do koje ce teren ici
	
	private static Random random = new Random();
	private static int seed = random.nextInt(10000000);
	
	private static int brojFunkcija = 4;  //broj funkcija koje ce se sabirati
	private static float upijac = 0.4f;  //broj koji smanjuje amplitutu kad se pomnoze isto se stepenuje
	private static float frekvencija = 2; //ucestanost koja ce se stepenovati
	
	private static String izborFunkcije = "Smoothinterp";
	private static Terrain terrain;
	
	private static int nacinRacunanjea = 1;

	
	
	public static int getNacinRacunanjea() {
		return nacinRacunanjea;
	}

	public static void setNacinRacunanjea(int nacinRacunanjea) {
		HeightGenerator.nacinRacunanjea = nacinRacunanjea;
		terrain.setFlag(1);
	}

	public static String getIzborFunkcije() {
		return izborFunkcije;
	}

	public static void setIzborFunkcije(String izborFunkcije) {
		HeightGenerator.izborFunkcije = izborFunkcije;
		terrain.setFlag(1);
	}

	public static int getBrojFunkcija() {
		return brojFunkcija;
	}

	public static void setBrojFunkcija(int brojFunkcija) {
		HeightGenerator.brojFunkcija = brojFunkcija;
		terrain.setFlag(1);
	}

	public static float getUpijac() {
		return upijac;
	}

	public static void setUpijac(float upijac) {
		HeightGenerator.upijac = upijac;
		terrain.setFlag(1);
	}

	public static float getFrekvencija() {
		return frekvencija;
	}

	public static void setFrekvencija(float frekvencija) {
		HeightGenerator.frekvencija = frekvencija;
		terrain.setFlag(1);
	}

	public static float getAMPLITUDE() {
		return AMPLITUDE;
	}

	public static void setAMPLITUDE(float aMPLITUDE) {
		AMPLITUDE = aMPLITUDE;
		terrain.setFlag(1);
	}

	public HeightGenerator(float gridX, float gridZ, Terrain ter) {
        terrain = ter;
    }
	
	/**
	 * funkcija koja ce za svaku tacku uzimati njene kordinate i vracati nasumicnu visinu
	 * prima kordinate tacke x, y 
	 * 
	 * offset doddat zato sto preko njega pokrecem teren odnosno ako su x i z bili 0,0 i ja hocu da pomerim teren
	 * za 5 piksela ja x i z ne diram vec pomerim offset za 5 sto efektivno cini isto, ovako radi jer su efektivne
	 * kordinate x i z vezane za mesto generisanja terena te njihova promena je procesorski zahtevna, dok promena 
	 * offseta efektivno pomera samo moj random broj generator, koji je zapucan na istom semenu pa generise broje broj 
	 * koji bi generisao za 5 piksela vise
	 */
	public float generateHeight(int x, int z, float xOffset, float zOffset) {
		float vrati = 0;
		float frek;
		for (int i = 0; i < brojFunkcija; i++) {
			
			if(nacinRacunanjea == 1)
				frek = (float) ((float) Math.pow(frekvencija, i)/Math.pow(1.5, 7-i));
			else
				frek = (float) ((float) Math.pow(frekvencija, i)/Math.pow(2, brojFunkcija-1));

				
			if(Math.pow(upijac, i) != 0) //mala optimiziacija
			vrati += getInterpolatedNoise((x+xOffset+10000)*frek, (z+zOffset+10000)*frek)* AMPLITUDE * Math.pow(upijac, i);
		}
		
		//vrati += getInterpolatedNoise(x/9f, z/9f) * AMPLITUDE;
		
		//System.out.println(vrati);
			
		
		return vrati;
		
	}
	
	private float getInterpolatedNoise(float x, float z) {
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;
		
		float v1 = getSmoothNoise(intX, intZ);
		float v2 = getSmoothNoise(intX + 1, intZ);
		float v3 = getSmoothNoise(intX, intZ + 1);
		float v4 = getSmoothNoise(intX + 1, intZ + 1);
		
		
		if(izborFunkcije.equals("Smoothinterp")) {
			float i1 = smoothinterp(v1, v2, fracX); 
			float i2 = smoothinterp(v3, v4, fracX);
			return smoothinterp(i1, i2, fracZ);
		}else if(izborFunkcije.equals("Cosinterp")) {
			float i1 = cosinterp(v1, v2, fracX);
			float i2 = cosinterp(v3, v4, fracX);
			return cosinterp(i1, i2, fracZ);
		}else if(izborFunkcije.equals("Stepinterp")){
			float i1 = stepinterp(v1, v2, fracX);
			float i2 = stepinterp(v3, v4, fracX);
			return stepinterp(i1, i2, fracZ);
		}else{
			float i1 = linterp(v1, v2, fracX);
			float i2 = linterp(v3, v4, fracX);
			return linterp(i1, i2, fracZ);
		}
	}
	
	
	/**
	 * Vrsi step interpolaciju izmedju dve tacke
	 * izmedju je broj izmedju 0 i 1 koji pokazuje koliko je blizu trazena tacka tacki x
	 * (<0.5 to je tacka x, >0.5 to je tacka z,)
	 */
	private float stepinterp(float x, float z, float izmedju) {
		
		if(izmedju > 0.5f)
			return z;
		else
			return x;
		
	}	
	
	/**
	 * Vrsi linearnu interpolaciju izmedju dve tacke
	 * izmedju je broj izmedju 0 i 1 koji pokazuje koliko je blizu trazena tacka tacki x
	 * (0 to je tacka x, 1 to je tacka z, 0,5 izmedju x i z)
	 */
	private float linterp(float x, float z, float izmedju) {
		return x + (z-x)*izmedju;
	}
	
	/**
	 * Vrsi cosinusnu interpolaciju  izmedju dve tacke
	 * izmedju je broj izmedju 0 i 1 koji pokazuje koliko je blizu trazena tacka tacki x
	 * (0 to je tacka x, 1 to je tacka z, 0,5 izmedju x i z)
	 */
	private float cosinterp(float x, float z, float izmedju) {
		double theta = izmedju * Math.PI;
        float f = (float)(1f - Math.cos(theta)) * 0.5f;
        return x * (1f - f) + z * f;
	}
	
	/**
	 * t^2(3-2t)
	 * Vrsi smooth step interpolaciju  izmedju dve tacke
	 * izmedju je broj izmedju 0 i 1 koji pokazuje koliko je blizu trazena tacka tacki x
	 * (0 to je tacka x, 1 to je tacka z, 0,5 izmedju x i z)
	 */
	private float smoothinterp(float x, float z, float izmedju) {
        float f = (float) (Math.pow(izmedju, 2) * (3 - 2*izmedju));
        return x * (1f - f) + z * f;
	}
	
	
	/**
	 * Posto random funkcija moze da skace od 1 do -1 za susedne tacke mi zelimo to da ublazimo
	 * to radimo tako sto saberemo visine centralne tacke sa tackama koje je okruzuju
	 * uz odredjeni faktor uticaja ono deljenje na kraju
	 */
	private float getSmoothNoise(int x, int z) {
		float corners = (getNoise(x-1, z-1) + getNoise(x+1, z-1) + getNoise(x+1, z+1)+ getNoise(x-1, z+1))/8f;
		float sides = (getNoise(x-1, z) + getNoise(x+1, z) + getNoise(x, z+1)+ getNoise(x, z+1))/4f;
		float center =  getNoise(x, z)/2f;
		return corners + center + sides;
	}
	
	/**
	 * funkcija koja vraca  float vrednost izmedju -1 i 1
	 * random.setSeed sluzi da za isto seme vrati istu fukciju, x i y su pomnozeni da bliska vrednosti imaju vise razlika
	 */
	private float getNoise(int x, int z) {
		//random.setSeed(x*432432 + z*535351 + seed); 
		random.setSeed(x*55228 + z*62457+ seed); 
		return random.nextFloat() * 2f - 1f;
	}
}

