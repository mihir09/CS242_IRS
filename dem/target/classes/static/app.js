// JavaScript source code
/*const searchForm = document.querySelector('.search-form');
let text = document.getElementById('text');
let index = document.getElementById('index');

searchForm.addEventListener('submit', (e) => {
    e.preventDefault();
    console.log('submit clicked')

    let formData = {
        text: text.value,
        index: index.value
    }

    console.log(formData);*/

    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/test/');
    xhr.setRequestHeader('content-type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            let json = JSON.parse(xhr.responseText);
            document.getElementById("search").hidden = true;
        }
    }
    xhr.send(JSON.stringify(formData));
//})