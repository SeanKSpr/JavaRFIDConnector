package edu.auburn.eng.sks0024.rfid_connector;
import java.util.ArrayList;
import java.util.List;

/**
 * EPCConverter is a static class which takes an Electronic Product Code (EPC) following the GS1/EPCglobal Tag Data Standard
 * and extracts the  corresponding UPC from it. The final decimal digit of the UPC isn't retrieved since 
 * it is impossible to recover, but this shouldn't be necessary since it is only the check bit.
 * 
 * @since 	1 (2-5-2015)
 * @version 1 (2-5-2015)
 * @author Sean Spurlin
 *
 */
public final class EPCConverter {
	
	private static int[] prefixLenList = {40, 37, 34, 30, 27, 24, 20};
	private final static int TOTAL_BIT_LENGTH = 44;
	
	/**
	 * getUPC takes an EPC as a List of words and returns the corresponding UPC. Note that a word is 16-bits so each entry in the list contains
	 * 16-bits or 4 hexadecimal digits. The total number of entries in the list is 6 as that is all that is required to represent a 96-bit EPC.
	 * (16 * 6 = 96)
	 * @param epc The Electronic Product Code (EPC) that you wish to extract the UPC. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @return The UPC corresponding to the input EPC as a String
	 */
	public static String getUPC(List<Integer> epc) {
		String companyPrefix, productReference, UPC;
		List<Integer> epcCopy = new ArrayList<Integer>(epc);
		extractHeader(epcCopy);
		extractFilter(epcCopy);
		
		int partitionIndex = extractPartition(epcCopy);
		int prefixBitLength = prefixLenList[partitionIndex];
		int productRefBitLength = TOTAL_BIT_LENGTH - prefixBitLength;
		
		companyPrefix = extractCompanyPrefix(epcCopy, prefixBitLength);
		productReference = extractProductReference(epcCopy, productRefBitLength);
		
		UPC = companyPrefix + productReference;
		
		while (UPC.length() < 11) {
			UPC = "0" + UPC;
		}
		
		int checkDigit = generateCheckDigit(UPC);
		UPC += checkDigit;
		return UPC;
		
	}
	
	/**
	 * Extracts and returns the product reference number within the EPC. This reference number is used as the second half of the UPC.
	 * @param epc The EPC that you wish to extract the product reference number. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @param productRefBitLength The length in bits of the product reference number (determinable by 44 bits - size of the company prefix)
	 * @return The product reference of the EPC as a String
	 */
	private static String extractProductReference(List<Integer> epc,
			int productRefBitLength) {
		String productRefBinary = "", productRefDecimal;
		int leftOverBits;
		String leadingZeroCheck;
		leftOverBits = productRefBitLength;
				
		if (productRefBitLength > 10) {
			leftOverBits = productRefBitLength - 10;
			leadingZeroCheck = Integer.toBinaryString(epc.get(2));
			while (leadingZeroCheck.length() < 16) {
				leadingZeroCheck = "0" + leadingZeroCheck;
			}
			leadingZeroCheck = leadingZeroCheck.substring(16 - leftOverBits, 16);
			productRefBinary += leadingZeroCheck;
		}
		
		leadingZeroCheck = Integer.toBinaryString(epc.get(3));
		while (leadingZeroCheck.length() < 16) {
			leadingZeroCheck = "0" + leadingZeroCheck;
		}
		
		leadingZeroCheck = leadingZeroCheck.substring(10 - leftOverBits, 10);
		productRefBinary += leadingZeroCheck;
		productRefDecimal = "" + Integer.parseInt(productRefBinary, 2);
		return productRefDecimal;
	}
	
	/**
	 * Extracts the company prefix from the input EPC. The company prefix is the first part of the corresponding UPC.
	 * @param epc The EPC you wish you extract the company prefix. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @param prefixBitLength The length in bits of the company prefix. This is determinable by the value you get when extracted the 
	 * partition bits and viewing the prefixLenList. The value of the partition bits is the index of the table.
	 * @return The company prefix of the input EPC as a String.
	 */
	private static String extractCompanyPrefix(List<Integer> epc,
			int prefixBitLength) {
		String prefixBinary = "", prefixDecimal;
		int leftOverBits, fullEntries;
		
		prefixBinary += Integer.toBinaryString(epc.get(0) & 0x0003);
		
		fullEntries = (prefixBitLength - 2) / 16;
		int i;
		for (i = 1; i < fullEntries + 1; i++) {
			String leadingZeroCheck = Integer.toBinaryString(epc.get(i));
			while (leadingZeroCheck.length() < 16) {
				leadingZeroCheck = "0" + leadingZeroCheck;
			}
			prefixBinary += leadingZeroCheck;
		}
		leftOverBits = (prefixBitLength - 2) % 16;
		String tempLeftOverBits = Integer.toBinaryString(epc.get(i));
		while (tempLeftOverBits.length() < 16) {
			tempLeftOverBits = "0" + tempLeftOverBits;
		}
		tempLeftOverBits = tempLeftOverBits.substring(0, leftOverBits);
		prefixBinary += tempLeftOverBits;
		prefixDecimal = "" + Integer.parseInt(prefixBinary, 2);
		return prefixDecimal;
	}
	
	/**
	 * Extracts the partition information of the EPC. This is used to determine the sizes of the company prefix and product reference numbers.
	 * The number returned by this function is used as an index into the prefixLenList to get the size of the company prefix.
	 * @param epc The EPC you wish to extract the partition bits from. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @return The partition bits of the EPC as an integer.
	 */
	private static int extractPartition(List<Integer> epc) {
		//Need to extract bits 11 through 13 (3 bits and we are assuming that the first bit is bit 0)
		//Most significant bit is bit 0
		int bits0Thru15 = epc.get(0);
		int bits11Thru13 = bits0Thru15 >>> 2;
		bits11Thru13 = bits11Thru13 & 0x0007;
		epc.set(0, bits0Thru15 & 0x0003);
		return bits11Thru13;
	}
	
	/**
	 * Extracts the filter information of the EPC. Currently the returned value is unused, but you can consult 
	 * http://www.gs1us.org/DesktopModules/Bring2mind/DMX/Download.aspx?EntryId=361&Command=Core_Download&PortalId=0&TabId=73
	 * to learn more information about this particular EPC standard and what the filter bits are used for.
	 * @param epc The EPC that you wish to extract the filter bits from. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @return The filter bits of the EPC
	 */
	private static int extractFilter(List<Integer> epc) {
		//Need to extract bits 8 through 10 (that's 3 bits and we are assuming that the first bit is bit 0)
		int bits0Thru15 = epc.get(0);
		int bits8Thru10 = bits0Thru15 >>> 5;
		bits8Thru10 = bits8Thru10 & 0x0007;
		epc.set(0, bits0Thru15 & 0x001f);
		return bits8Thru10;
		
	}
	
	/**
	 * Extracts the header information of the EPC. Currently the returned value is unused, but you can consult
	 * http://www.gs1us.org/DesktopModules/Bring2mind/DMX/Download.aspx?EntryId=361&Command=Core_Download&PortalId=0&TabId=73
	 * for more information on the EPC header.
	 * @param epc The EPC that you wish to extract the header bits from. EPC must be a List of Integers with a 16-bit word in each entry.
	 * @return The header bits of the EPC
	 */
	private static int extractHeader(List<Integer> epc) {
		//Need to extract first 8 bits. These are located in the first index of the list
		//Each index of the list contains a Word (4 hexadecimal digits/16 binary digits)
		//So chop off the first two hexadecimal digits
		//Just save the first 8 bits (these will be returned)
		//And the first 8 bits of the first index of the list with 0
		//Return the saved bits
		
		int bits0Thru15 = epc.get(0);
		int bits0Thru7 = bits0Thru15 >>> 8;
		epc.set(0, bits0Thru15 & 0x00ff);
		return bits0Thru7;
	}
	
	/**
	 * Generates a check digit for the UPC following the standardized formula which can be found at 
	 * http://www.gs1.org/how-calculate-check-digit-manually
	 * @param baseUPC The UPC prior to its check digit being generated 
	 * @return The check digit of the UPC as an integer
	 */
	private static int generateCheckDigit(String baseUPC) {
		int oddSum = 0, evenSum = 0, checkDigit;
		for (int i = 0; i < baseUPC.length(); i++) {
			int temp = Integer.parseInt(baseUPC.substring(i, i + 1));
			if ((i + 1) % 2 == 0) evenSum += temp;
			else oddSum += 3 * temp;
		}
		checkDigit = 10 - ((oddSum + evenSum) % 10);
		
		if (checkDigit == 10) {
			checkDigit = 0;
		}
		
		return checkDigit;
		
	}
	
	/**
	 * Returns the serial number of the EPC. This is a unique identifier for products.
	 * @param epc Electronic Product Code stored as a list of integers
	 * @return Serial of the EPC as a long
	 */
	public static long getSerial(List<Integer> epc) {
		int bits57thru62 = epc.get(3) & 0x003f;
		int bits63thru78 = epc.get(4);
		int bits79thru95 = epc.get(5);
		
		String bits57thru62Str, bits63thru78Str, bits79thru95Str;
		
		bits57thru62Str = Integer.toBinaryString(bits57thru62);
		bits63thru78Str = Integer.toBinaryString(bits63thru78);
		bits79thru95Str = Integer.toBinaryString(bits79thru95);
		
		while (bits57thru62Str.length() < 6) {
			bits57thru62Str = "0" + bits57thru62Str;
		}
		while (bits63thru78Str.length() < 16) {
			bits63thru78Str = "0" + bits63thru78Str;
		}
		while (bits79thru95Str.length() < 16) {
			bits79thru95Str = "0" + bits79thru95Str;
		}
		
		String serialBinary = bits57thru62Str + bits63thru78Str + bits79thru95Str;
		
		long serial = Long.parseLong(serialBinary, 2);
		
		return serial;
	}
	//Simple optimal test for the class. Runs the first item in the excel spreadsheet. This test succeeds.
	public static void main(String[] args) {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x031d);
		testList.add(0xfc53);
		testList.add(0x2dc0);
		testList.add(0x0000);
		testList.add(0x0001);
		
		int header = extractHeader(testList);
		int filter = extractFilter(testList);
		String prefix = extractCompanyPrefix(testList, 24);
		String productReference = extractProductReference(testList, 20);
		String UPC = getUPC(testList);
		
		ArrayList<Integer> testInstance2 = new ArrayList<Integer>();
		testInstance2.add(0x3034);
		testInstance2.add(0x031d);
		testInstance2.add(0xfc53);
		testInstance2.add(0x2dc0);
		testInstance2.add(0x0000);
		testInstance2.add(0x0002);
		
		String Instance2UPC = getUPC(testInstance2);
		assert(UPC.compareTo(Instance2UPC) == 0);
	}
}
