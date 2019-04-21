package br.com.alexandredev.repository

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import br.com.alexandredev.domain.Author

@Repository
interface AuthorRepository : MongoRepository<Author, String> {
}