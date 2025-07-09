-- Tiếp tục thêm câu hỏi
INSERT INTO questions (question_text, option1, option2, option3, option4, answer_nr, category, difficulty) VALUES

-- PHẦN 41: KIẾN THỨC NÂNG CAO
('Trong trường hợp nào được phép vượt đèn đỏ?', 'Không có trường hợp nào được phép', 'Khi có xe cứu thương phía sau', 'Khi đường vắng', 'Ban đêm không có xe', 1, 'Luật giao thông', 'Dễ'),

('Khi điều khiển xe trên đường cao tốc, việc làm nào bị nghiêm cấm?', 'Lùi xe, quay đầu xe', 'Dừng xe, đỗ xe', 'Chạy quá tốc độ quy định', 'Tất cả các ý trên', 4, 'Luật giao thông', 'Trung bình'),

-- PHẦN 42: KỸ THUẬT LÁI XE CHUYÊN SÂU
('Khi vào cua người lái xe cần thực hiện theo thứ tự:', 'Giảm tốc độ - Về số - Đánh lái', 'Đánh lái - Giảm tốc độ - Về số', 'Về số - Đánh lái - Giảm tốc độ', 'Tất cả đều sai', 1, 'Kỹ thuật lái xe', 'Khó'),

('Khi xuống dốc cao và dài, người lái xe cần:', 'Tắt máy để tiết kiệm xăng', 'Về số thấp và控制tốc độ', 'Đi số không (số N)', 'Đạp phanh liên tục', 2, 'Kỹ thuật lái xe', 'Khó'),

-- PHẦN 43: XỬ PHẠT VI PHẠM
('Vi phạm nồng độ cồn ở mức cao nhất, người điều khiển xe máy bị phạt:', '4-5 triệu đồng', '6-8 triệu đồng', '16-18 triệu đồng', '30-40 triệu đồng', 2, 'Xử phạt', 'Trung bình'),

('Điều khiển xe máy chạy quá tốc độ trên 20 km/h bị phạt:', '1-2 triệu đồng', '2-3 triệu đồng', '3-4 triệu đồng', '4-5 triệu đồng', 4, 'Xử phạt', 'Trung bình'),

-- PHẦN 44: TÌNH HUỐNG KHẨN CẤP
('Khi động cơ xe bị cháy, người lái xe cần:', 'Tìm cách dập lửa bằng bình cứu hỏa', 'Nhanh chóng thoát khỏi xe', 'Gọi cứu hỏa', 'Tất cả các ý trên', 4, 'Xử lý tình huống', 'Khó'),

('Khi xe bị ngập nước làm chết máy, người lái xe không nên:', 'Cố nổ máy nhiều lần', 'Kiểm tra bộ lọc gió', 'Kiểm tra bugi', 'Đẩy xe đến chỗ khô ráo', 1, 'Xử lý tình huống', 'Khó'),

-- PHẦN 45: BIỂN BÁO PHỤ
('Biển phụ đặt dưới biển cấm đỗ xe "Từ 20h đến 6h" có ý nghĩa:', 'Cấm đỗ xe trong khoảng thời gian đó', 'Được đỗ xe trong khoảng thời gian đó', 'Cấm đỗ xe tất cả các giờ', 'Được đỗ xe tất cả các giờ', 1, 'Biển báo', 'Trung bình'),

('Biển phụ "Khoảng cách đến nơi nguy hiểm" có ý nghĩa:', 'Báo khoảng cách đến nơi nguy hiểm', 'Báo chiều dài đoạn đường nguy hiểm', 'Báo chiều rộng cầu đường', 'Báo độ dốc của đường', 1, 'Biển báo', 'Trung bình'),

-- PHẦN 46: VĂN HÓA GIAO THÔNG NÂNG CAO
('Khi xảy ra tai nạn giao thông, người có văn hóa giao thông phải:', 'Dừng lại giúp đỡ người bị nạn', 'Gọi cấp cứu và công an', 'Bảo vệ hiện trường', 'Tất cả các ý trên', 4, 'Văn hóa giao thông', 'Trung bình'),

('Người có văn hóa giao thông khi điều khiển xe phải:', 'Chấp hành đúng luật', 'Điều khiển xe đúng tốc độ', 'Nhường nhịn người khác', 'Tất cả các ý trên', 4, 'Văn hóa giao thông', 'Dễ'),

-- PHẦN 47: BẢO VỆ MÔI TRƯỜNG
('Để giảm ô nhiễm môi trường, người lái xe cần:', 'Bảo dưỡng xe định kỳ', 'Không chở quá tải', 'Không điều chỉnh xe nẹt pô', 'Tất cả các ý trên', 4, 'Môi trường', 'Dễ'),

('Xe máy gây ô nhiễm môi trường do:', 'Khói thải', 'Tiếng ồn', 'Xăng dầu rò rỉ', 'Tất cả các ý trên', 4, 'Môi trường', 'Dễ'),

-- PHẦN 48: SƠ CẤP CỨU
('Khi gặp người bị tai nạn giao thông bị chảy máu nhiều, cần:', 'Băng bó vết thương', 'Cầm máu tạm thời', 'Gọi cấp cứu', 'Tất cả các ý trên', 4, 'Sơ cứu', 'Khó'),

('Khi gặp người bị tai nạn giao thông bị gãy xương, cần:', 'Nẹp cố định tạm thời', 'Cho uống thuốc giảm đau', 'Di chuyển nạn nhân', 'Để nguyên không động vào', 1, 'Sơ cứu', 'Khó'),

-- PHẦN 49: TÌNH HUỐNG ĐẶC BIỆT
('Khi gặp đoàn xe tang, người điều khiển xe phải:', 'Giảm tốc độ', 'Không được cắt ngang đoàn xe tang', 'Không bấm còi', 'Tất cả các ý trên', 4, 'Xử lý tình huống', 'Dễ'),

('Khi gặp đoàn người đi bộ có người dẫn đường, người lái xe phải:', 'Giảm tốc độ', 'Đi từ từ qua đoàn người', 'Bấm còi báo hiệu', 'Tất cả các ý trên', 1, 'Xử lý tình huống', 'Dễ'),

-- PHẦN 50: AN TOÀN GIAO THÔNG
('Khi tham gia giao thông vào ban đêm, người đi xe máy cần:', 'Mặc quần áo sáng màu', 'Bật đèn chiếu sáng', 'Giảm tốc độ phù hợp', 'Tất cả các ý trên', 4, 'An toàn', 'Trung bình'),

('Để đảm bảo an toàn khi tham gia giao thông, cần:', 'Chấp hành quy định giao thông', 'Giữ khoảng cách an toàn', 'Điều khiển xe với tốc độ phù hợp', 'Tất cả các ý trên', 4, 'An toàn', 'Dễ');
