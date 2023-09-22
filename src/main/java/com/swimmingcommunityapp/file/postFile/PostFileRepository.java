package com.swimmingcommunityapp.file.postFile;


import com.swimmingcommunityapp.file.postFile.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile,Long> {


}
