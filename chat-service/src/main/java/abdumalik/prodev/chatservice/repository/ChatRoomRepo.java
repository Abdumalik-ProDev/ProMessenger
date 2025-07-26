package abdumalik.prodev.chatservice.repository;

import abdumalik.prodev.chatservice.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepo extends MongoRepository<ChatRoom, String> {
    List<ChatRoom> findByParticipantIdsContainsOrderByLastMessageAtDesc(UUID userId);
}