import urllib.request


def run():
    url = "https://www.artstation.com/artwork?sorting=trending&medium=digital2d&category=matte_painting"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    # htmltext = urllib.request.urlopen(url).read().decode("utf-8")
    htmltext = urllib.request.urlopen(req).read().decode("utf-8")
    text_file = open("Output.txt", "w")
    text_file.write(htmltext)
    text_file.close()



if __name__ == '__main__':
    run()
