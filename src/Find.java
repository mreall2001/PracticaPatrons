import java.util.ArrayList;

public class Find {
    private String text;

    public Find(String text) {
        this.text = text;
    }

    public boolean match(String stringPat) {
        boolean sonIguales = false;
        ArrayList<Character> listaText = crearList(text);
        ArrayList<Character> listaMatch = checkMatch(stringPat);

        if (listaMatch.size() > listaText.size() || listaMatch.isEmpty()) {
            return false;
        }

        for (int i = 0; i < listaText.size(); i++) {
            if (sonIguales) break;
            if (listaText.get(i) == listaMatch.get(0) || listaMatch.get(0) == '®') {
                for (int j = 0; j < listaMatch.size(); j++) {
                    if (listaMatch.get(j) == '®'){
                        sonIguales = true;
                    } else if (listaText.get(j+i) != listaMatch.get(j)) {
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

    public ArrayList<Character> crearList(String text) {
        ArrayList<Character> listaFinal = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            listaFinal.add(text.charAt(i));
        }
        return listaFinal;
    }

    public ArrayList<Character> checkMatch(String text){
        ArrayList<CharacterTipo.Tipo> listaTipos = clasificarCaracteres(text);
        ArrayList<Character> listaText = crearList(text);
        ArrayList<Character> listaFinal = new ArrayList<>();

        for (int i = 0; i < listaTipos.size(); i++) {
            if (listaTipos.get(i) == CharacterTipo.Tipo.ALFANUMERICO){
                listaFinal.add(listaText.get(i));
            } else if (listaTipos.get(i) == CharacterTipo.Tipo.ARROBA) {
                listaFinal.add(listaText.get(i+1));
                i++;
            } else if (listaTipos.get(i) == CharacterTipo.Tipo.INTERROGANTE) {
                listaFinal.add('®');
            }
        }

        return listaFinal;



    }

    public ArrayList<CharacterTipo.Tipo> clasificarCaracteres(String text){
        ArrayList<CharacterTipo.Tipo> listaTipos = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '@'){
                listaTipos.add(CharacterTipo.Tipo.ARROBA);
            } else if (text.charAt(i) == '?') {
                listaTipos.add(CharacterTipo.Tipo.INTERROGANTE);
            } else {
                listaTipos.add(CharacterTipo.Tipo.ALFANUMERICO);
            }
        }
        return listaTipos;
    }





    public String capture(String stringPat) {
        return "";
    }


}
