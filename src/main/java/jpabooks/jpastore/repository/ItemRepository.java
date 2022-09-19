package jpabooks.jpastore.repository;

import jpabooks.jpastore.domain.item.Item;
import jpabooks.jpastore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            em.persist(item);
        } else{
            em.merge(item);
        }

    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();

    }
}
