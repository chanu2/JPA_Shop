package jpabooks.jpastore.repository;

import jpabooks.jpastore.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;   //boot에서는 PersistenceContext를 autowired로 바꿀 수 있다

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class , id);
    }

    public List<Member> findAll(){

        return em.createQuery("select m from Member m", Member.class).getResultList();  // 인라인 변수 단축

    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name ",Member.class) .setParameter("name",name).getResultList();
    }


}
