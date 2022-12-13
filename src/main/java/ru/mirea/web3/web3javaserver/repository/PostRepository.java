package ru.mirea.web3.web3javaserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.web3.web3javaserver.entity.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Page<Post> findByOrderByDateDesc(Pageable pageable);
    Page<Post> findByUser_TokenOrderByDateDesc(String token, Pageable pageable);
    Long countByUser_Token(String token);
}
