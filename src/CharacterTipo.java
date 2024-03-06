
public class CharacterTipo {

    enum Tipo{
        INTERROGANTE,
        ARROBA,
        ALFANUMERICO
    }
    private Tipo tipo;


    public Tipo clasificarLetra(char letra){

        if (letra == '?'){
            tipo = Tipo.INTERROGANTE;
        } else if (letra == '@') {
            tipo = Tipo.ARROBA;
        }else {
            tipo = Tipo.ALFANUMERICO;
        }

        return tipo;
    }




}
