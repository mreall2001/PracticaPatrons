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
            if (text.charAt(i) == listaMatch.get(0).ch || listaMatch.get(0).ch == '®' || listaMatch.get(0).ch == '['){
                for (int j = 0; j < listaMatch.size(); j++) {
                    if (listaMatch.get(j).ch == '®'){
                        sonIguales = true;
                    } else if (listaMatch.get(j).ch == '©') {
                        sonIguales = false;
                    } else if (listaMatch.get(j).ch == '▓') {
                        for (int k = 0; k < listaMatch.get(j).set.length(); k++) {
                            if (text.charAt(i+j) == listaMatch.get(j).set.charAt(k)){
                                sonIguales = true;
                                break;
                            } else {
                                sonIguales = false;
                            }
                        }
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
                // He de llegir tots els caràctes del patró fins que arribi a ]
                // Guardar tots aquests caràcters dins un string
                for (int j = i+1; j < stringPath.length(); j++) {
                    if (stringPath.charAt(j) == ']'){
                        i = j;
                        break;
                    } else {
                        t.set += stringPath.charAt(j);
                    }

                }
                t.ch = '▓';
                listaTipos.add(t);
                System.out.println(t.set);
            } else {
                CharacterTipo t = new CharacterTipo(CharacterTipo.Tipo.ALFANUMERICO);
                t.ch = stringPath.charAt(i);
                listaTipos.add(t);
            }
        }
        return listaTipos;
    }





    public String capture(String stringPat) {
        return "";
    }


}
