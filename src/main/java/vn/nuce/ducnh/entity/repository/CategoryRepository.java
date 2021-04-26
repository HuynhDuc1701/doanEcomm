package vn.nuce.ducnh.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.nuce.ducnh.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
}
