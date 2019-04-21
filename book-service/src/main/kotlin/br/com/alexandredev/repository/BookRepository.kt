package br.com.alexandredev.repository

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import br.com.alexandredev.domain.Book

@Repository
interface BookRepository : MongoRepository<Book, String> {
}