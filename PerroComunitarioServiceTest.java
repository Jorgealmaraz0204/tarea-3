package com.tarea.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PerroComunitarioServiceTest {

    PerroRepository mockRepository;
    PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba(){
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() {        
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

        // Act
        Perro resultado = service.obtenerPerroPorNombre("Fido");

        // Assert
        assertEquals("Fido", resultado.getNombre());
        assertEquals(4, resultado.getEdad());
    
    }
    
    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {        
        // Ejecución que lanza excepción

        when(mockRepository.buscarPorNombre("Rex")).thenReturn(null);

        //service.obtenerPerroPorNombre("Rex"); 
        assertThrows(PerroNoEncontradoException.class, () -> service.obtenerPerroPorNombre("Rex"));
           
    }
    
    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
        // Ejecución que lanza excepción

        assertThrows(IllegalArgumentException.class, () -> service.obtenerPerroPorNombre(null));
        //service.obtenerPerroPorNombre(null);        
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        // Ejecución que lanza excepción
        //service.obtenerPerroPorNombre(null);
        assertThrows(IllegalArgumentException.class, () -> service.obtenerPerroPorNombre(""));
    }


    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        // Verificación
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

       
        service.obtenerPerroPorNombre("Fido");

        
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
        //verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}
