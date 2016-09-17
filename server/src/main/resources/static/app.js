/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */

function refr() {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {

            var fetched = xhr.response;
            if (fetched) {

                var el = document.createElement('html');
                el.innerHTML = fetched;

                var bodyNew = el.getElementsByClassName("online-update");
                var bodyOld = document.getElementsByTagName("online-update");
                for (var i = 0; i < bodyNew.length; i++) {
                    morphdom(bodyOld[i], bodyNew[i]);
                }
            }

            setTimeout(refr, 5000);
        }
    };

    xhr.open('GET', window.location.href, true);
    xhr.send();
}

setTimeout(refr, 5000);
