//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;

//clasa principala - aplicatie JAVA
class Polyglot {
    //metoda privata pentru conversie low-case -> up-case folosind functia toupper() din R
    private static String RToUpper(String token){
        //construim un context care ne permite sa folosim elemente din R
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei funcitiei R, toupper(String)
        //pentru aexecuta instructiunea I din limbajul X, folosim functia graalvm polyglot.eval("X", "I");
        Value result = polyglot.eval("R", "toupper(\""+token+"\");");
        //utilizam metoda asString() din variabila incarcata cu output-ul executiei pentru a mapa valoarea generica la un String
        String resultString = result.asString();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultString;
    }

    //metoda privata pentru evaluarea unei sume de control simple a literelor unui text ASCII, folosind PYTHON
    private static int SumCRC(String token){
        //construim un context care ne permite sa folosim elemente din PYTHON
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei functiei PYTHON, sum()
        //avem voie sa inlocuim anumite elemente din scriptul pe care il construim spre evaluare, aici token provine din JAVA, dar va fi interpretat de PYTHON
        Value result = polyglot.eval("python", "sum(ord(ch) for ch in '" + token + "')");
        //utilizam metoda asInt() din variabila incarcata cu output-ul executiei, pentru a mapa valoarea generica la un Int
        int resultInt = result.asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultInt;
    }

    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();
        //construim un array de string-uri, folosind cuvinte din pagina web:  https://chrisseaton.com/truffleruby/tenthings/
        Value array = polyglot.eval("js", "[\"ab\",\"ba\",\"aa\",\"aa\",\"aa\"];");
        //pentru fiecare cuvant, convertim la upcase folosind R si calculam suma de control folosind PYTHON
        for (int i = 0; i < array.getArraySize();i++){
            String element = array.getArrayElement(i).asString();
            String upper = RToUpper(element);
            int crc = SumCRC(upper);
            System.out.println(upper + " -> " + crc);
        }
        Boolean[] viz = new Boolean[(int)array.getArraySize()];
        for(int i=0; i< viz.length; ++i){
            viz[i] = false;
        }
        for (int i = 0; i < array.getArraySize()-1; i++){
            viz[i] = true;
            String elementi = array.getArrayElement(i).asString();
            String upperi = RToUpper(elementi);
            int crci = SumCRC(upperi);
            for (int j = i+1; j < array.getArraySize(); j++){
                String elementj = array.getArrayElement(j).asString();
                String upperj = RToUpper(elementj);
                int crcj = SumCRC(upperj);
                if(crci == crcj && viz[j] == false){
                    System.out.println("Suma elem " + upperi + " = " + "suma elem " + upperj);
                    viz[j] = true;
                }
            }
        }
        // inchidem contextul Polyglot
        polyglot.close();
    }
}

