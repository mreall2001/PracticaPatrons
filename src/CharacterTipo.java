
public class CharacterTipo {

    enum Tipo{
        INTERROGANTE,
        ARROBA,
        ALFANUMERICO,
        PORCENTAJE,
        DOLAR,
        CORCHETE
    }
    private Tipo tipo;
    char ch;
    String set = "";

    public CharacterTipo (Tipo tipo){
        this.tipo = tipo;
    }

}
