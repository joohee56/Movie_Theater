# 예약
## 예약 흐름
영화 선택, 극장 선택 → 시간 선택 → 좌석 선택 → 예약 확인

## 상영 시간표 조회
- 사용자가 원하는 영화의 상영 시간 조회
- 특정 영화관에서의 시간별 상영 정보 조회

## 좌석 선택 및 예약 가능 여부 확인
- 좌석을 선택할 시, 해당 좌석 예약 가능 여부 실시간 확인
- 자석이 이미 예약된 경우, 다른 좌석을 선택하도록 안내

## 예약 생성
- 좌석을 선택 후 예약을 확정하면 Booking에 정보 저장
- 예약 완료 시, 예약 내역을 조회하거나 취소할 수 있도록 정보 저장

## 예약 취소
- 기존 예약을 취소할 수 있도록 Booking의 status 필드 업데이트

## 예약 확인
- 예약된 내역을 확인할 수 있도록 Bookinng 정보를 사용자에게 제공
