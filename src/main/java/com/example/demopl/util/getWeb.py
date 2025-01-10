import requests
from bs4 import BeautifulSoup
import re
import time


def send(url):
  headers = {
      "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
      "accept-language": "zh-CN,zh;q=0.9,ja;q=0.8,zh-TW;q=0.7",
      "cache-control": "no-cache",
      "cookie": "Hm_lvt_ef8d5b3eafdfe7d1bbf72e3f450ad2ed=1735866017,1736493390; HMACCOUNT=15DD73F0AC612CCD; cf_clearance=tKgiyV4GoAZM60PW_KZgZwxQ2QMzJsK2RMrVyNDyxXA-1736495145-1.2.1.1-_X9nW.6_qrIWscejY1CyOj_VVcH45nRZPrRaXBg7MjxqqfkqOfPUBtsDutYmnZ6lszPI7qroZxsxp5K3BJPI2waKOKYXqPZydC5Z16Y5OPyNgOPL69_6YuuhCysYRg1h22W2ggRDz77MhxLORPN_cXCQV5tNsx3X1F48yrDW70IjA.xOrKegalnIgVtFfUb2bP5APgE2XWmLvb19KaowqcCfi3BXb0ovnfv8CqEuL_7En6J7B8wwooiqe43Mi8MiPhjdUhK0ey29_8lNCyk7GBtScIRJ6JaLMc4OgBzYYK7SrfV1FexBeNcYkULZfQZWYNBHLvyKv6CXxhd0FZudMIA4lu.DPRlFxxYC40wP_chyIbbuPKCHAuVKjBwyOovlv156c2t.aCszlO_NuDZ38g; jieqiRecentRead=3410.185308.0.1.1736495526.0; Hm_lpvt_ef8d5b3eafdfe7d1bbf72e3f450ad2ed=1736495529",
      "pragma": "no-cache",
      "priority": "u=0, i",
      "referer": "https://www.linovelib.com/novel/3410/catalog",
      "sec-ch-ua": "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"",
      "sec-ch-ua-mobile": "?0",
      "sec-ch-ua-platform": "\"Windows\"",
      "sec-fetch-dest": "document",
      "sec-fetch-mode": "navigate",
      "sec-fetch-site": "same-origin",
      "sec-fetch-user": "?1",
      "upgrade-insecure-requests": "1",
      "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36"
  }
  response = requests.get(url, headers=headers)
  return response.text

def getNext(html):
    # 使用正则匹配 nextpage 的值
    match = re.search(r'var nextpage="(.*?)";', html)
    if match:
        return "https://www.linovelib.com"+match.group(1)  # 返回匹配的 URL
    return None  # 如果没有匹配到，返回 None

def getTxt(html):
    soup=BeautifulSoup(html, 'html.parser')
    output_file = "D:\pyScript\quanzhi.txt"
    chapter_title = soup.find('h1').get_text(strip=True)
    # 提取正文内容
    text_content_div = soup.find('div', id='TextContent')
    paragraphs = text_content_div.find_all('p')
    text_content = "\n".join(p.get_text(strip=True) for p in paragraphs)

    # 输出结果
    print("章节名:")
    print(chapter_title)
    print("\n正文内容:")
    print(text_content)
    with open(output_file, "a", encoding="utf-8") as file:
        file.write(chapter_title + "\n")
        file.write(text_content + "\n")


def process_page(url,count):
    html = send(url)  # 获取 HTML 内容
    getTxt(html)  # 提取正文

    # 获取下一页的 URL
    next_url = getNext(html)
    if next_url:
        process_page(next_url)  # 递归处理下一页


def possNov(url,count):
    if(count>5):
        return
    html=send(url)
    getTxt(html)
    nextUrl=getNext(html)
    count += 1
    time.sleep(1)
    possNov(nextUrl,count)


possNov("https://www.linovelib.com/novel/3410/185308.html",0)