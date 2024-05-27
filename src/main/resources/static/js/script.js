var deleteUserModal = document.getElementById('deleteUserModal');
deleteUserModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var userId = button.getAttribute('data-id');
    var userIdInput = deleteUserModal.querySelector('#userIdToDelete');
    userIdInput.value = userId;
});

var editUserModal = document.getElementById('editUserModal');
editUserModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var userId = button.getAttribute('data-id');
    var username = button.getAttribute('data-username');
    var name = button.getAttribute('data-name');
    var surname = button.getAttribute('data-surname');
    var email = button.getAttribute('data-email');

    var modalIdInput = editUserModal.querySelector('#editUserId');
    var modalUsernameInput = editUserModal.querySelector('#editUsername');
    var modalNameInput = editUserModal.querySelector('#editFirstName');
    var modalSurnameInput = editUserModal.querySelector('#editLastName');
    var modalEmailInput = editUserModal.querySelector('#editEmailAddress');

    modalIdInput.value = userId;
    modalUsernameInput.value = username;
    modalNameInput.value = name;
    modalSurnameInput.value = surname;
    modalEmailInput.value = email;
});

function sortTable(table, column, asc = true) {
    const dirModifier = asc ? 1 : -1;
    const tBody = table.tBodies[0];
    const rows = Array.from(tBody.querySelectorAll("tr"));

    const sortedRows = rows.sort((a, b) => {
        const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
        const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim();

        return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);
    });

    while (tBody.firstChild) {
        tBody.removeChild(tBody.firstChild);
    }

    tBody.append(...sortedRows);

    table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc"));
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle("th-sort-asc", asc);
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle("th-sort-desc", !asc);
}

document.querySelector("#sortByName").addEventListener("click", () => {
    const tableElement = document.querySelector("table");
    const currentIsAscending = document.querySelector("#sortByName").classList.contains("th-sort-asc");
    sortTable(tableElement, 2, !currentIsAscending);
});

document.querySelector("#sortBySurname").addEventListener("click", () => {
    const tableElement = document.querySelector("table");
    const currentIsAscending = document.querySelector("#sortBySurname").classList.contains("th-sort-asc");
    sortTable(tableElement, 3, !currentIsAscending);
});




