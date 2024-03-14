import java.util.ArrayList;

public class Find {
    private String text;

    public Find(String text) {
        this.text = text;
    }

    public boolean match(String stringPat) {
        boolean sonIguales = false;
        ArrayList<CharacterTipo> listaMatch = clasificarCaracteres(stringPat);

        if (listaMatch.size() > text.length() || listaMatch.isEmpty()) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            if (sonIguales)break;
            if (text.charAt(i) == listaMatch.get(0).ch || listaMatch.get(0).ch == '®' || listaMatch.get(0).ch == '▓'){
                for (int j = 0; j < listaMatch.size(); j++) {
                    if (listaMatch.get(j).ch == '®'){
                        sonIguales = true;
                    } else if (listaMatch.get(j).ch == '©') {
                        sonIguales = false;
                    } else if (listaMatch.get(j).ch == '▓') {
                        sonIguales = false;
                        break;
                    } else if (text.charAt(i+j) != listaMatch.get(j).ch) {
                        sonIguales = false;
                        break;
                    } else {
                        sonIguales = true;
                    }
                }
            }
        }
        return sonIguales;
    }

    public ArrayList<CharacterTipo> clasificarCaracteres(String stringPath){
        ArrayList<CharacterTipo> listaTipos = new ArrayList<>();

        for (int i = 0; i < stringPath.length(); i++) {
            if (stringPath.charAt(i) == '@'){
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.ARROBA);
                t.ch = stringPath.charAt(i+1);
                listaTipos.add(t);
                i++;
            } else if (stringPath.charAt(i) == '?') {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.INTERROGANTE);
                t.ch = '®';
                listaTipos.add(t);
            } else if (stringPath.charAt(i) == '%') {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.PORCENTAJE);
                if (i == 0 && stringPath.charAt(i+1) == text.charAt(0)){
                    t.ch = stringPath.charAt(i+1);
                    listaTipos.add(t);
                    i++;
                } else {
                    t.ch = '»';
                    listaTipos.add(t);
                }
            } else if (stringPath.charAt(i) == '$' && i+1 == stringPath.length()) {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.DOLAR);
                if (text.charAt(text.length()-1) != listaTipos.get(listaTipos.size()-1).ch){
                    t.ch = '©';
                    listaTipos.set(listaTipos.size()-1, t);
                }
            } else if (stringPath.charAt(i) == '[') {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.CORCHETE);
                for (int j = i+1; j < stringPath.length(); j++) {
                    if (stringPath.charAt(j) == ']'){
                        i = j;
                        break;
                    } else {
                        t.set += stringPath.charAt(j);
                    }
                }
                t.ch = checkClaudator(t.set, listaTipos);
                listaTipos.add(t);
            } else {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.ALFANUMERICO);
                t.ch = stringPath.charAt(i);
                listaTipos.add(t);
            }
        }
        return listaTipos;
    }

    private char checkClaudator(String set, ArrayList<CharacterTipo> listaTipos) {
        set = checkSet(set);
        char letraCorrecta = 0;
        boolean sonIguales = false;
        int contadorText = -1;

        if (listaTipos.isEmpty()){
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < set.length(); j++) {
                    if (text.charAt(i) == set.charAt(j)){
                        letraCorrecta = set.charAt(j);
                        sonIguales = true;
                        break;
                    }else {
                        letraCorrecta = '▓';
                    }
                }
                if (sonIguales) break;
            }
        }else {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == listaTipos.get(0).ch || listaTipos.get(0).ch == '®' || listaTipos.get(0).ch == '▓'){
                    for (int j = 0; j < listaTipos.size(); j++) {
                        if (text.charAt(i+j) == listaTipos.get(j).ch){
                            sonIguales = true;
                        } else if (listaTipos.get(j).ch == '®') {
                            sonIguales = true;
                        } else {
                            sonIguales = false;
                            break;
                        }
                        contadorText++;
                    }
                }else if (sonIguales) {
                    for (int j = 0; j < set.length(); j++) {
                        if (text.charAt(i+contadorText) == set.charAt(j)){
                            letraCorrecta = set.charAt(j);
                            break;
                        }else {
                            letraCorrecta = '▓';
                        }
                    }
                    break;
                }
            }
        }




        return letraCorrecta;
    }

    private String checkSet(String set) {
        String resultado = "";
        char startChar = 0;
        char endChar = 0;

        for (int i = 0; i < set.length(); i++) {
            if (set.charAt(i) == '-'){
                startChar = set.charAt(i-1);
                endChar = set.charAt(i+1);
                for (char c = startChar; c <= endChar; c++) {
                    resultado += c;
                }
            }else {
                resultado += set.charAt(i);
            }
        }
        return resultado;
    }


    public String capture(String stringPat) {
        return "";
    }


}
