package service.core;//package service.core;

/**
 * Interface to define the state to be stored in service.core.ClientInfo objects
 * 
 * @author Rem
 *
 */
public class ClientInfo {

	public static final char MALE				= 'M';
	public static final char FEMALE				= 'F';

	public ClientInfo() {}

	public ClientInfo(String name, char sex, int age, int points, int noClaims, String licenseNumber) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noClaims = noClaims;
		this.licenseNumber = licenseNumber;
	}

	/**
	 * Public fields are used as modern best practice argues that use of set/get
	 * methods is unnecessary as (1) set/get makes the field mutable anyway, and
	 * (2) set/get introduces additional method calls, which reduces performance.
	 */
	public String name;
	public char gender;
	public int age;
	public int points;
	public int noClaims;
	public String licenseNumber;
}