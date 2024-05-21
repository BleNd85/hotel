var deleteRoomModal = document.getElementById('deleteRoomModal');
deleteRoomModal.addEventListener('show.bs.modal', function (e) {
    var button = e.relatedTarget;
    var roomId = button.getAttribute('data-id');
    var roomIdInput = deleteRoomModal.querySelector('#roomIdToDelete');
    roomIdInput.value = roomId;
});