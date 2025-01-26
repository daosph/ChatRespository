package com.chatSockets.chatSockets.repository;


import com.chatSockets.chatSockets.dto.RolDto;
import com.chatSockets.chatSockets.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    @Query("SELECT r.id AS id, r.nombre AS nombre FROM Rol r")
    List<RolDto> findAllRolDto();
}
