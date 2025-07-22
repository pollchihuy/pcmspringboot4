package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    Page<Supplier> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    Page<Supplier> findByAlamatContainsIgnoreCase(String nama, Pageable pageable);

    List<Supplier> findByNamaContainsIgnoreCase(String nama);
    List<Supplier> findByAlamatContainsIgnoreCase(String nama);

    @Query(value = "SELECT s FROM Supplier s WHERE s.id IN ?1")
    List<Supplier> cariList(List<Long> list, Pageable pageable);

    Optional<Supplier> findTop1ByOrderByIdDesc();

//    public List<DealInfo> getDealInfos(List<String> dealIds) {
//        String queryStr = "SELECT NEW com.admin.entity.DealInfo(deal.url, deal.url, deal.url, deal.url, deal.price, deal.value) " + "FROM Deal AS deal where deal.id in :inclList";
//        TypedQuery<DealInfo> query = em.createQuery(queryStr, DealInfo.class);
//        query.setParameter("inclList", dealIds);
//        return query.getResultList();
//    }
}
