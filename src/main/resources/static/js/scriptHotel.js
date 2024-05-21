var deleteHotelModal = document.getElementById('deleteHotelModal');
deleteHotelModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var hotelId = button.getAttribute('data-id');
    var hotelIdInput = deleteHotelModal.querySelector('#hotelIdToDelete');
    hotelIdInput.value = hotelId;
});