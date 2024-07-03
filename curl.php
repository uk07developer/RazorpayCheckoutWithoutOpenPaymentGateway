
curl -u YOUR_KEY_ID:YOUR_KEY_SECRET \
-X POST https://api.razorpay.com/v1/orders \
-H "Content-Type: application/json" \
-d '{
    "amount": 50000,
    "currency": "INR",
    "receipt": "order_rcptid_11",
    "payment_capture": 1
}'