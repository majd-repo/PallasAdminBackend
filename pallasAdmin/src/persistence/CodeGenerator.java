package persistence;

public class CodeGenerator {

	public String getNext(String lastCodTransfer) {
		String current = lastCodTransfer;
		Character charPart = current.charAt(0);
		String numPart = current.substring(1, current.length());
		Integer nextNumPart = Integer.parseInt(numPart) + 1;
		if (nextNumPart < 10)
			return charPart + "00" + nextNumPart.toString();
		else if (nextNumPart >= 10 && nextNumPart < 100)
			return charPart + "0" + nextNumPart.toString();
		else if (nextNumPart > 999) {
			int ascii = (int) charPart;
			ascii = ascii + 1;
			charPart = (char) ascii;
			nextNumPart = 0;
			return charPart + "001";
		}
		return charPart + nextNumPart.toString();
	}

}
