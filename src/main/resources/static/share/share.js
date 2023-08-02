const shareKakao = () => {
    const uri = "http://localhost:8080";
    const title = "";
    const description = "";
    const thumbnail = "";

    Kakao.Share.sendDefault({
       objectType: 'feed',
       content: {
           title: `${title}`,
           description: `${description}`,
           imageUrl: `${thumbnail}`,
           imageWidth: 800,
           imageHeight: 400,
           link: {
               mobileWebUrl: `${uri}`,
               webUrl: `${uri}`,
           },
       },
        buttons: [
            {
                title: '앱으로 보기',
                link: {
                    mobileWebUrl: `${uri}`,
                    webUrl: `${uri}`,
                }
            }
        ]
    });
};

const shareMessage = () => {
    const url = '';
    const device = navigator.userAgent.toLowerCase();

    const smsLink = ("android".match(device))
        ? "sms://?body=" + encodeURIComponent(url)
        : "sms://&body=" + encodeURIComponent(url);

    console.log(smsLink);
    window.open(smsLink);
};

const shareClipboard = () => {
    const url = '';
    
    navigator.clipboard.writeText(url)
        .then(() => {
            console.log("성공");
            alert("성공");
        })
        .catch(err => {
            console.log(err);
            alert("실패");
        });
};