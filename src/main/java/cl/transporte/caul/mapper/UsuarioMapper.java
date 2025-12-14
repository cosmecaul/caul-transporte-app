package cl.transporte.caul.mapper;

import cl.transporte.caul.model.Usuario;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsuarioMapper {

    @Select("""
            SELECT 
              id,
              nombre_usuario  AS nombreUsuario,
              password_hash   AS passwordHash,
              nombre_completo AS nombreCompleto,
              email,
              rut,
              rol,
              id_cliente      AS idCliente,
              activo,
              created_at      AS createdAt,
              updated_at      AS updatedAt
            FROM usuario
            WHERE nombre_usuario = #{username}
            """)
    Usuario findByUsername(String username);

    @Select("""
            SELECT 
              id,
              nombre_usuario  AS nombreUsuario,
              password_hash   AS passwordHash,
              nombre_completo AS nombreCompleto,
              email,
              rut,
              rol,
              id_cliente      AS idCliente,
              activo,
              created_at      AS createdAt,
              updated_at      AS updatedAt
            FROM usuario
            WHERE id = #{id}
            """)
    Usuario findById(Long id);

    @Select("""
            SELECT 
              id,
              nombre_usuario  AS nombreUsuario,
              password_hash   AS passwordHash,
              nombre_completo AS nombreCompleto,
              email,
              rut,
              rol,
              id_cliente      AS idCliente,
              activo,
              created_at      AS createdAt,
              updated_at      AS updatedAt
            FROM usuario
            ORDER BY nombre_usuario
            """)
    List<Usuario> findAll();

    // Opción 1: boolean con CASE WHEN
    @Select("""
            SELECT 
              CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM usuario
            WHERE nombre_usuario = #{username}
            """)
    boolean existsByUsername(String username);

    // Si prefieres con int:
//    @Select("SELECT COUNT(*) FROM usuario WHERE nombre_usuario = #{username}")
//    int countByUsername(String username);

    @Insert("""
            INSERT INTO usuario (
              nombre_usuario,
              password_hash,
              nombre_completo,
              email,
              rut,
              rol,
              id_cliente,
              activo,
              created_at,
              updated_at
            ) VALUES (
              #{nombreUsuario},
              #{passwordHash},
              #{nombreCompleto},
              #{email},
              #{rut},
              #{rol},
              #{idCliente},
              #{activo},
              NOW(),
              NOW()
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUsuario(Usuario usuario);

    @Update("""
            UPDATE usuario SET
              nombre_completo = #{nombreCompleto},
              email           = #{email},
              rut             = #{rut},
              rol             = #{rol},
              id_cliente      = #{idCliente},
              activo          = #{activo},
              password_hash   = #{passwordHash},
              updated_at      = NOW()
            WHERE id = #{id}
            """)
    void updateUsuario(Usuario usuario,Long id);
}

