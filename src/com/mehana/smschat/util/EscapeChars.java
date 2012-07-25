package com.mehana.smschat.util;

/**
 * 
 * @author maruen
 * email: maruen@gmail.com
 *
 */

public final class EscapeChars {

    public static final String escapeCharsToSMS(String s){
        StringBuffer sb = new StringBuffer();
        int n = s.length();
        for (int i = 0; i < n; i++) {
           char c = s.charAt(i);
           switch (c) {
              case 'à': sb.append("a");break;
              case 'À': sb.append("A");break;
              case 'â': sb.append("a");break;
              case 'Â': sb.append("A");break;
              case 'ä': sb.append("a");break;
              case 'Ä': sb.append("A");break;
              case 'Ã': sb.append("A");break;
              case 'ã': sb.append("a");break;
              case 'å': sb.append("a");break;
              case 'á': sb.append("a");break;
              case 'Å': sb.append("A");break;
              case 'ç': sb.append("c");break;
              case 'Ç': sb.append("C");break;
              case 'é': sb.append("e");break;
              case 'É': sb.append("E");break;
              case 'È': sb.append("E");break;
              case 'ê': sb.append("e");break;
              case 'Ê': sb.append("E");break;
              case 'Ë': sb.append("E");break;
              case 'ï': sb.append("i");break;
              case 'í': sb.append("i");break;
              case 'ô': sb.append("o");break;
              case 'õ': sb.append("o");break;
              case 'Õ': sb.append("O");break;
              case 'Ô': sb.append("O");break;
              case 'ö': sb.append("o");break;
              case 'ó': sb.append("o");break;
              case 'Ö': sb.append("O");break;
              case 'ù': sb.append("u");break;
              case 'Ù': sb.append("U");break;
              case 'û': sb.append("u");break;
              case 'Û': sb.append("U");break;
              case 'ü': sb.append("u");break;
              case 'Ü': sb.append("U");break;
              default:  sb.append(c); break;
           }
        }
        return sb.toString();
    }
}
