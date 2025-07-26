package abdumalik.prodev.chatservice.service;

import abdumalik.prodev.chatservice.model.ChatRoom;
import abdumalik.prodev.chatservice.repository.ChatRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepo chatRoomRepository;

    public String getChatRoomId(UUID senderId, UUID recipientId) {
        if (senderId == null || recipientId == null) {
            throw new IllegalArgumentException("User IDs cannot be null");
        }
        List<UUID> ids = Arrays.asList(senderId, recipientId);
        Collections.sort(ids);
        return ids.get(0) + "_" + ids.get(1);
    }

    public ChatRoom findOrCreateChatRoom(UUID senderId, UUID recipientId) {
        String chatRoomId = getChatRoomId(senderId, recipientId);
        return chatRoomRepository.findById(chatRoomId)
                .map(chatRoom -> {
                    chatRoom.setLastMessageAt(LocalDateTime.now());
                    return chatRoomRepository.save(chatRoom);
                })
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setId(chatRoomId);
                    newChatRoom.setParticipantIds(Set.of());
                    newChatRoom.setLastMessageAt(LocalDateTime.now());
                    return chatRoomRepository.save(newChatRoom);
                });
    }

}