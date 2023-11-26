package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.model.repository.RoomRepository;
import com.epam.training.ticketservice.service.implementation.RoomServiceImpl;
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
    private Room ROOM2 = new Room("Pedersoli", 5,5);
    @Test
    void testCreateRoomWhenRoomIsNotExist() {
        //Given
        //When
        underTest.createRoom(ROOM.getRoomName(), ROOM.getChairRow(), ROOM.getChairColumn());
        //Then
        verify(roomRepository).save(ROOM);
    }

    @Test
    void testCreateRoomWhenRoomIsAlreadyExist() {
        // Given
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.createRoom(ROOM.getRoomName(), ROOM.getChairRow(), ROOM.getChairColumn()));
        // Then
        verify(roomRepository, never()).save(ROOM);
    }

    @Test
    void testUpdateRoomWhenRoomIsAlreadyExist() {
        //Given
        Room expected = new Room("Leonardo DiCaprio", 9, 9);
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        //When
        underTest.updateRoom("Leonardo DiCaprio", 9, 9);
        //Then
        verify(roomRepository).save(expected);
    }

    @Test
    void testUpdateRoomWhenRoomIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.updateRoom("Leonardo DiCaprio", 9, 9));
        // Then
        verify(roomRepository, never()).save(ROOM);
    }

    @Test
    void testDeleteRoomWhenRoomIsAlreadyExist() {
        //Given
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        //When
        underTest.deleteRoom(ROOM.getRoomName());
        //Then
        verify(roomRepository).delete(ROOM);
    }

    @Test
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
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        given(roomRepository.findByRoomName(ROOM2.getRoomName())).willReturn(Optional.of(ROOM2));
        List<Room> rooms = List.of(ROOM, ROOM2);
        given(roomRepository.findAll()).willReturn(rooms);

        // When
        String actual = underTest.listRooms();
        String expected = ROOM.toString() + "\n" + ROOM2.toString() + "\n";

        // Then
        assertEquals(expected, actual);

    }

    @Test
    void testListRoomsWhenRoomsAreNotAlreadyExist() {
        // Given
        // When
        String actual = underTest.listRooms();
        String expected = "There are no rooms at the moment";
        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findAll();
    }
}
