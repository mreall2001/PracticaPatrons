
public class CharacterTipo {

    enum Tipo{
        INTERROGANTE,
        ARROBA,
        ALFANUMERICO,
        PORCENTAJE,
        DOLAR,
        ESPACIO
    }
    private Tipo tipo;

    public CharacterTipo (Tipo tipo){
        this.tipo = tipo;
    }




}
