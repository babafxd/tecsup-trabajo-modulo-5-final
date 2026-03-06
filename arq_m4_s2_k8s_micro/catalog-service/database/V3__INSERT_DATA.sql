-- ============================================
-- Migration: V3__INSERT_DATA.sql
-- ============================================

INSERT INTO products (name, description, price, stock, category, created_by) VALUES
('Pizza Hawaina XL', 'Pizza Hawaina XL con extra queso', 45.99, 15, 'ALIMENTOS', 1),
('Hamburguesa de carne', 'Hamburguesa doble de carne con queso', 21.99, 50, 'ALIMENTOS', 1),
('Hamburguesa de pollo', 'Hamburguesa doble de carne con queso', 9.99, 30, 'ALIMENTOS', 2),
('Pie de Limón"', 'Pie de Limón personal', 12.99, 8, 'PASTELERIA', 2),
('Selva negra', 'Selva negra personal', 15.99, 20, 'PASTELERIA', 3);