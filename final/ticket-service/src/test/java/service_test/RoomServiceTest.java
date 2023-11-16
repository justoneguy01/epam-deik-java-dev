package service_test;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class RoomServiceTest {
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);
    private Room ROOM = new Room("Leonardo DiCaprio", 10,10);
    @Test // CreateRoom +
    void testCreateRoomWhenRoomIsNotExist() {
        //Given
        //When
        underTest.createRoom(ROOM.getRoomName(), ROOM.getChairRow(), ROOM.getChairColumn());
        //Then
        verify(roomRepository).save(ROOM);
    }

    @Test // CreateRoom -
    void testCreateRoomWhenRoomIsAlreadyExist() {
        // Given
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.createRoom(ROOM.getRoomName(), ROOM.getChairRow(), ROOM.getChairColumn()));
        // Then
        verify(roomRepository, never()).save(ROOM);
    }

    @Test // UpdateRoom +
    void testUpdateRoomWhenRoomIsAlreadyExist() {
        //Given
        Room expected = new Room("Leonardo DiCaprio", 9, 9);
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        //When
        underTest.updateRoom("Leonardo DiCaprio", 9, 9);
        //Then
        verify(roomRepository).save(expected);
    }

    @Test // UpdateRoom -
    void testUpdateRoomWhenRoomIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.updateRoom("Leonardo DiCaprio", 9, 9));
        // Then
        verify(roomRepository, never()).save(ROOM);
    }

    @Test // DeleteRoom +
    void testDeleteRoomWhenRoomIsAlreadyExist() {
        //Given
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        //When
        underTest.deleteRoom(ROOM.getRoomName());
        //Then
        verify(roomRepository).delete(ROOM);
    }

    @Test // DeleteRoom -
    void testDeleteRoomWhenRoomIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () ->  underTest.deleteRoom(ROOM.getRoomName()));
        // Then
        verify(roomRepository, never()).save(ROOM);
    }

    @Test
    void testListRoomsWhenRoomsAreAlreadyExist() {
        // Given
        given(roomRepository.findAll()).willReturn(List.of(ROOM));
        // When
        List<Room> actual = underTest.listRooms();
        // Then
        assertEquals(List.of(ROOM), actual);
        verify(roomRepository).findAll();

    }

    @Test
    void testListRoomsWhenRoomsAreNotAlreadyExist() {
        // Given
        // When
        List<Room> result = underTest.listRooms();
        // Then
        assertTrue(result.isEmpty());
        verify(roomRepository).findAll();
    }
}
