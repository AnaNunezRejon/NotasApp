package com.example.notasapp;

import com.example.notasapp.view.fragments.FragmentInicio;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test simplificado para verificar que FragmentInicio existe
 * y puede instanciarse correctamente en el contexto de pruebas locales.
 *
 * Nota: Las pruebas LOCALES no pueden cargar Activities ni UI reales.
 */
public class ExampleUnitTest {

    @Test
    public void fragmentInicio_instanciacionCorrecta() {
        FragmentInicio fragment = new FragmentInicio();

        // Verificar que el fragmento no es null
        assertNotNull("El fragmento FragmentInicio no se pudo instanciar", fragment);

        // Verificar que pertenece a la clase correcta
        assertEquals("La clase del fragment no coincide",
                "com.example.notasapp.view.fragments.FragmentInicio",
                fragment.getClass().getName());
    }
}
