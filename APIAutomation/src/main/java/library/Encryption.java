package library;

public class Encryption
{
	public static void main(String[] args)
	{
		//System.out.println(Encryption.decrypt("XO4 qgncUSGY"));
	}

	private static final String[]	ALPHABETS	= new String[] { "0@", "1#", "2$", "3%", "4^", "5&", "6*", "7 = ", "8-", "9+", "Aa", "Bb", "Cc", "Dd", "Ee", "Ff", "Gg", "Hh", "Ii", "Jj", "Kk", "Ll", "Mm", "Nn", "Oo", "Pp", "Qq", "Rr", "Ss", "Tt", "Uu", "Vv", "Ww", "Xx", "Yy", "Zz" };
	private static int				x;
	private static int				placement;
	private static int				ref;
	private static String			firstWrite;
	private static String			secondWrite;
	private static String			thirdWrite;
	private static final String		CODE_PHRASE	= "Les chaussettes de l'archiduchesse sont elles seches et archi seches";

	private static String charToString(final char character)
	{
		String str = Character.valueOf(character).toString();
		return str;
	}

	private static boolean check(final String letter)
	{
		boolean bill = false;
		for (int i = 0; i < 36; i++)
		{
			if (isSameString(ALPHABETS[i].substring(0, 1), letter) || isSameString(ALPHABETS[i].substring(1, 2), letter))
			{
				bill = true;
			}
		}
		return bill;
	}

	private static String code1(final String codePhrase, final String inputString, final int longer, final int sage)
	{
		int shift = 0;
		int orig;
		if (check(charToString(inputString.charAt(placement))))
		{
			if (check(charToString(codePhrase.charAt(ref))))
			{
				if (sage == 0)
				{
					shift = findshift(charToString(codePhrase.charAt(ref)));
				}
				if (sage == 1)
				{
					shift = 36 - findshift(charToString(codePhrase.charAt(ref)));
				}
			}
			else
			{
				shift = 0;
			}
			orig = findshift(charToString(inputString.charAt(placement)));
			firstWrite = firstWrite + ALPHABETS[fixoffset(orig + shift)].charAt(x);
		}
		else
		{
			firstWrite = firstWrite + inputString.charAt(placement);
		}
		secondWrite = inputString.substring(placement + 1, inputString.length());
		thirdWrite = firstWrite + secondWrite;

		if (placement == inputString.length() - 1)
		{
			return thirdWrite;
		}
		placement++;
		if (ref == longer)
		{
			ref = 0;
		}
		else
		{
			ref++;
		}
		String code1Str = code1(codePhrase, inputString, longer, sage);
		return code1Str;
	}

	public static String decrypt(final String inputString)
	{
		String decryptStr = decrypt(CODE_PHRASE, inputString);
		return decryptStr;
	}

	public static String decrypt(final String codePhrase, final String inputString)
	{
		String decryptStr = process(codePhrase, inputString, 1);
		return decryptStr;
	}

	public static String encrypt(final String inputString)
	{
		String encryptStr = encrypt(CODE_PHRASE, inputString);
		return encryptStr;
	}

	public static String encrypt(final String codePhrase, final String inputString)
	{
		String encryptStr = process(codePhrase, inputString, 0);
		return encryptStr;
	}

	private static int findshift(final String letter)
	{
		int shift = -1;
		for (int i = 0; i < 36; i++)
		{
			if (isSameString(ALPHABETS[i].substring(0, 1), letter) || isSameString(ALPHABETS[i].substring(1, 2), letter))
			{
				if (isSameString(ALPHABETS[i].substring(0, 1), letter))
				{
					x = 0;
				}
				if (isSameString(ALPHABETS[i].substring(1, 2), letter))
				{
					x = 1;
				}
				shift = i;
				break;
			}
		}
		return shift;
	}

	private static int fixoffset(final int takin)
	{
		int offSet = takin;
		if (takin > 35)
		{
			offSet = takin - 36;
		}
		return offSet;
	}

	private static String process(final String codePhrase, final String inputString, final int process)
	{
		String phraseStr = null;
		if (isEmptyString(inputString) || isEmptyString(codePhrase))
		{
			phraseStr = inputString;
		}
		else
		{
			final int longer = codePhrase.length() - 1;
			placement = 0;
			ref = 0;
			secondWrite = "";
			firstWrite = "";
			thirdWrite = "";
			phraseStr = code1(codePhrase, inputString, longer, process);
		}
		return phraseStr;
	}

	public static boolean isSameString(final String string1, final String string2)
	{
		boolean isSame = false;
		if (string1 == null && string2 == null)
		{
			isSame = true;
		}
		else if (string1 == null || string2 == null)
		{
			isSame = false;
		}
		else
		{
			isSame = string1.toString().equals(string2.toString());
		}
		return isSame;
	}

	public static boolean isEmptyString(final String obj)
	{
		boolean isEmpty = false;
		if (obj == null)
		{
			isEmpty = true;
		}
		else
		{
			isEmpty = obj.trim().isEmpty();
		}
		return isEmpty;
	}
}