package ru.mirea.web3.web3javaserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mirea.web3.web3javaserver.entity.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Page<Post> findByOrderByDateDesc(Pageable pageable);
    Page<Post> findByUser_TokenOrderByDateDesc(String token, Pageable pageable);
    Long countByUser_Token(String token);

    @Modifying
    @Query(value = "DELETE FROM public.post WHERE (id = :id)", nativeQuery = true)
    void deletePostById(@Param("id") Integer id);
}
