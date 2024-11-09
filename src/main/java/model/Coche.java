package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coche implements Serializable{ // Tenemos que serializarlo para poder ver el fichero

    // version UID
    private final static long serialVersionUID = 1234L;

    //propiedades de Coche
    private int id;
    private String matricula,marca,modelo,color;


    // lo preparamos para que no se puedan repetir ni los id ni las matriculas


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coche coche)) return false;
        return id == coche.id || Objects.equals(matricula, coche.matricula);
        // tenemos que cambiar el && del return para que se compruebe tanto la matrícula como el id
        //a la hora de buscar coincidencias
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricula);
    }
}
