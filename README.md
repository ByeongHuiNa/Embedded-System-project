# Embedded-System-project
### [A] 프로젝트 명
PIR센서 기반 야생동물 감시 시스템
### [B] 배경 및 아이디어
배경 : 야생동물로 인한 농작물 피해가 빈번히 발생
아이디어 : PIR센서를 사용해 동물이 인식이 되면 경고음을
주거나 농가 주인에게 연락이 가도록 응용 가능. 
### [C] 프로젝트 내용
시스템 구조
 - PIR센서 : 야생동물 감지
 - LCD디스플레이 :  사람일수도 있으니 경고문구 출력
 - LED 모듈 :  PIR센서가 감지되면 경고등(빨간색)을 구현하려 했으나 구현하지 못하       였음.
 - CoAP 서버 :  CoAP 통신사용
 - CoAP클라이언트 :  PIR센서 모니터링

### [D] 하드웨어 구현
PIR; Ground, GPIO 0, 5V
LCD DISPLAY: Ground, 5V, I2C SDA(GPIO 8), I2C SCL(GPIO 9)
LED : GROUND, Red(GPIO 2)
![image](https:![image](https://user-images.githubusercontent.com/87258864/147630515-dd4e19e2-25da-4fe0-962d-7af2c23b145e.png)



### [E] 개발 결과물을 사용하는 방법(구동화면 스크린 샷 첨부)

메뉴화면에서 방향키로 하이라이트부분을 이동하고 엔터키를 사용해서 선택을 합니다.
게임화면에서는 ‘q’버튼을 눌러 다시 메뉴 화면으로 돌아갈 수 있습니다.
 
 ![image](https://user-images.githubusercontent.com/86523413/144700104-4328b239-d889-4562-bac7-b45e024fe7b1.png)

### [F] 개발 결과물의 필요성 및 활용방안
짧은 여가시간동안 다양한 게임을 즐길 수 있다.
