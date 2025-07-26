package abdumalik.prodev.chatservice.repository;

import abdumalik.prodev.chatservice.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepo extends MongoRepository<ChatMessage, String> {
    Page<ChatMessage> findByChatRoomIdOrderByTimestampDesc(String chatRoomId, Pageable pageable);
}