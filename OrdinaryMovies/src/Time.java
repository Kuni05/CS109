public class Time {
        private int hour, minute;
        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public String toString() {
            return String.format("%02d:%02d", hour, minute);
        }

        public int toMinutes() {
            return hour * 60 + minute;
        }
}
