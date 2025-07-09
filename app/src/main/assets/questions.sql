-- Create questions table
CREATE TABLE IF NOT EXISTS questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question_text TEXT NOT NULL,
    option1 TEXT NOT NULL,
    option2 TEXT NOT NULL,
    option3 TEXT NOT NULL,
    option4 TEXT NOT NULL,
    answer_nr INTEGER NOT NULL,
    category TEXT,
    difficulty TEXT,
    image TEXT,
    explanation TEXT
);

-- Delete all existing questions
DELETE FROM questions;

-- Insert traffic questions
INSERT INTO questions (question_text, option1, option2, option3, option4, answer_nr, category, difficulty, image, explanation) VALUES
-- PHẦN KHÁI NIỆM
('Khái niệm "đường bộ" được hiểu như thế nào là đúng?', 'Đường bộ là đường dành cho xe cơ giới, xe thô sơ và người đi bộ', 'Đường bộ là công trình công cộng được xây dựng gồm: đường, cầu, hầm...', 'Đường bộ là đường chỉ dành cho xe cơ giới và xe thô sơ', 'Đường bộ là đường dành cho xe cơ giới và người đi bộ', 2, 'Khái niệm', 'Dễ', NULL, 'Theo Luật Giao thông đường bộ, đường bộ gồm đường, cầu, hầm và các công trình phụ trợ khác'),

-- PHẦN BIỂN BÁO
('Biển báo này có ý nghĩa gì?', 'Cấm xe mô tô', 'Cấm xe mô tô và xe gắn máy', 'Cấm xe đạp', 'Hết cấm xe mô tô', 1, 'Biển báo', 'Dễ', 'bien_p104.jpg', 'Đây là biển báo P.104 "Cấm xe mô tô", cấm tất cả các loại xe mô tô đi qua'),

('Biển báo này có ý nghĩa gì?', 'Cấm đi ngược chiều', 'Cấm đi vào', 'Đường cấm', 'Cấm dừng xe và đỗ xe', 3, 'Biển báo', 'Dễ', 'bien_p101.jpg', 'Đây là biển báo P.101 "Đường cấm", cấm tất cả các loại phương tiện đi lại cả hai hướng'),

-- PHẦN TÌNH HUỐNG GIAO THÔNG
('Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?', 'Xe khách, xe tải, xe con', 'Xe con, xe khách, xe tải', 'Xe tải, xe khách, xe con', 'Xe khách, xe con, xe tải', 1, 'Tình huống', 'Khó', 'tinh_huong_1.jpg', 'Nguyên tắc: Xe ưu tiên -> Xe trên đường ưu tiên -> Xe đi từ bên phải -> Xe rẽ phải -> Xe đi thẳng -> Xe rẽ trái'),

('Xe con đi theo chiều mũi tên có vi phạm quy tắc giao thông không?', 'Có vi phạm', 'Không vi phạm', 'Không vi phạm nếu có xe phía sau', 'Không vi phạm nếu đường vắng', 1, 'Tình huống', 'Khó', 'tinh_huong_2.jpg', 'Xe con đã vi phạm vì đi ngược chiều và không đi đúng phần đường quy định'),

-- PHẦN SA HÌNH
('Xe nào vi phạm quy tắc giao thông?', 'Xe khách và xe con', 'Xe tải và xe khách', 'Xe khách, xe tải và xe con', 'Cả ba xe', 3, 'Sa hình', 'Khó', 'sa_hinh_1.jpg', 'Xe khách đi không đúng làn, xe tải vượt không đúng quy định, xe con đi ngược chiều');
