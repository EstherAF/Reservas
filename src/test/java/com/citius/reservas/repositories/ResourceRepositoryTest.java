/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.FinalResource;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.GenericResourceRepository;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml", "classpath:applicationContext-security-test.xml"})
public class ResourceRepositoryTest {

    @Autowired
    @Qualifier("resourceRepository")
    private GenericResourceRepository<Resource> resourceRepository;
    @Autowired
    @Qualifier("finalResourceRepository")
    private GenericResourceRepository<FinalResource> finalResourceRepository;
    @Autowired
    @Qualifier("resourceGroupRepository")
    private GenericResourceRepository<ResourceGroup> resourceGroupRepository;
    
    ResourceGroup rg;
    FinalResource fr;
    
    @Before
    @Transactional
    public void initialize(){
        
        rg = new ResourceGroup("Grupo prueba", resourceRepository.findPath());
        rg = resourceGroupRepository.create(rg);
        
        fr = new FinalResource("Recurso final de prueba", rg, "Descripción", (Integer)10);
        fr = finalResourceRepository.create(fr);
    }

    @Test
    public void testCreate(){
        assertNotNull("Id del grupo de recursos creado es nulo", rg.getId());
        assertNotNull("Id del recurso final creado es nulo", fr.getId());
    }
    
    @Test
    public void testFindAll() {
        List<Resource> l = resourceRepository.findAll();

        assertTrue("Lista no nula", l != null);
        assertTrue("Lista no vacia", !l.isEmpty());

        String nameClass = l.get(0).getClass().getSimpleName();
        assertTrue("Se conserva la clase original", !nameClass.equals(Resource.class.getSimpleName()));
    }

    @Test
    public void testFind() {
        Resource r = resourceRepository.find(1);

        assertTrue("No nulo", r != null);
        assertTrue("No tiene id", r.getId() != null);
    }

    @Test
    public void testFindByName() {
        Resource r = resourceRepository.findByName(fr.getName());

        assertTrue("No nulo", r != null);
        assertTrue("No tiene id", r.getId() != null);
    }
    
    @Test
    public void testFindByParent() {
        List<Resource> l = resourceRepository.findByParent((ResourceGroup) fr.getParent());

        assertTrue("Lista no nula", l != null);
        assertTrue("Lista no vacia", !l.isEmpty());
    }
}