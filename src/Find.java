import java.util.ArrayList;

public class Find {
    private String text;

    public Find(String text) {
        this.text = text;
    }

    public boolean match(String stringPat) {
        boolean sonIguales = false;
        ArrayList<Character> listaText = crearList(text);
        ArrayList<Character> listaMatch = crearList(stringPat);
        int contadorMatch = 0;

        if (listaMatch.size() > listaText.size() || listaMatch.isEmpty()) {
            return false;
        }

        for (int i = 0; i < listaText.size(); i++) {
            if (listaText.get(i) == listaMatch.get(0)){
                for (int j = 0; j < listaMatch.size(); j++) {
                    if (listaText.get(i+j) != listaMatch.get(j)){
                        sonIguales = false;
                        break;
                    }else {
                        sonIguales = true;
                    }
                }

            }
        }
        return sonIguales;
    }

    private ArrayList<Character> crearList(String text) {
        ArrayList<Character> listaFinal = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' '){
                listaFinal.add(text.charAt(i));
            }
        }
        return listaFinal;
    }

    public String capture(String stringPat) {
        return "";
    }


}
