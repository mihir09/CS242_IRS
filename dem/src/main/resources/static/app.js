// JavaScript source code
const searchForm = document.querySelector('.search-form');
let text = document.getElementById('text');
let index = document.getElementById('index');


searchForm.addEventListener('submit', (e) => {
    e.preventDefault();
    console.log('submit clicked')

    let xhr = new XMLHttpRequest(),
        params = 'text=' + text.value + '&index=' + index.value;
    xhr.open('GET', '/test?'+params, true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            console.log(xhr.responseText);
            let json = JSON.parse(xhr.responseText);
            document.getElementById("search").hidden = true;
            document.getElementById("response").hidden = false;
            for (var i = 0; i < json.length; i++) {
                var obj = json[i];
                console.log(obj.Url);
                console.log(obj.Text);
                let div = document.createElement('div');
                div.setAttribute("class", "card");
                let url = document.createElement('a');
                url.setAttribute("href", obj.Url);
                url.innerHTML = obj.Url;
                let text = document.createElement('p');
                text.innerHTML = obj.Text;
                div.appendChild(url);
                div.appendChild(text);
                document.getElementById("response").appendChild(div);
            }
        }
    }
    console.log(params);
    xhr.send(null);
})