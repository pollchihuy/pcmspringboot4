package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    //Derivered Query
    // SELECT x.* FROM MstUser x WHERE x.Email = ?
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIsRegistered(String username, Boolean valid);
    Optional<User> findByUsernameOrEmailOrNoHp(String username,String email,String noHp);
    Optional<User> findByUsernameOrEmailOrNoHpAndIsRegistered(String username, String email, String noHp, Boolean valid);

    public Page<User> findByNamaLengkapContainsIgnoreCase(String nama, Pageable pageable);
    public Page<User> findByAlamatContainsIgnoreCase(String nama, Pageable pageable);
    public Page<User> findByEmailContainsIgnoreCase(String nama, Pageable pageable);
    public Page<User> findByUsernameContainsIgnoreCase(String nama, Pageable pageable);
    public Page<User> findByNoHpContainsIgnoreCase(String nama, Pageable pageable);

    @Query(value = "SELECT x FROM User x WHERE CAST(DATEDIFF(year ,x.tanggalLahir,CURRENT_TIMESTAMP)AS STRING) LIKE CONCAT('%',?1,'%') ")
    public Page<User> cariUmur(String umur, Pageable pageable);

//    @Query(value = "SELECT x FROM User x WHERE lower(x.akses.nama) LIKE lower(CONCAT('%',?1,'%')) ")
//    public Page<User> cariAkses(String akses, Pageable pageable);

//    public Page<User> findByTanggalLahirContainsIgnoreCase(String nama, Pageable pageable);

    public List<User> findByNamaLengkapContainsIgnoreCase(String nama);
    public List<User> findByAlamatContainsIgnoreCase(String nama);
    public List<User> findByEmailContainsIgnoreCase(String nama);
    public List<User> findByUsernameContainsIgnoreCase(String nama);
    public List<User> findByNoHpContainsIgnoreCase(String nama);
    @Query(value = "SELECT x FROM User x WHERE CAST(DATEDIFF(year,x.tanggalLahir,current_timestamp) AS STRING ) LIKE CONCAT('%',?1,'%') ")
    public List<User> cariUmur(String umur);
//    @Query(value = "SELECT x FROM User x WHERE lower(x.akses.nama) LIKE lower(CONCAT('%',?1,'%')) ")
//    public List<User> cariAkses(String akses);
//    public List<User> findByTanggalLahirContainsIgnoreCase(String nama);
    public Optional<User> findTop1ByOrderByIdDesc();

}