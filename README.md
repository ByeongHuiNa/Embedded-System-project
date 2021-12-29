# Embedded-System-project
### [A] 프로젝트 명
PIR센서 기반 야생동물 감시 시스템
### [B] 배경 및 아이디어
 - 배경 : 야생동물로 인한 농작물 피해가 빈번히 발생
 - 아이디어 : PIR센서를 사용해 동물이 인식이 되면 경고음을
주거나 농가 주인에게 연락이 가도록 응용 가능. 
### [C] 프로젝트 내용
시스템 구조
 - PIR센서 : 야생동물 감지
 - LCD디스플레이 :  사람일수도 있으니 경고문구 출력
 - LED 모듈 :  PIR센서가 감지되면 경고등(빨간색)을 구현하려 했으나 구현하지 못하       였음.
 - CoAP 서버 :  CoAP 통신사용
 - CoAP클라이언트 :  PIR센서 모니터링

### [D] 하드웨어 구현
 - PIR: Ground, GPIO 0, 5V
 - LCD DISPLAY: Ground, 5V, I2C SDA(GPIO 8), I2C SCL(GPIO 9)
 - LED : GROUND, Red(GPIO 2)
![image](https:![image](https://user-images.githubusercontent.com/87258864/147630515-dd4e19e2-25da-4fe0-962d-7af2c23b145e.png)


