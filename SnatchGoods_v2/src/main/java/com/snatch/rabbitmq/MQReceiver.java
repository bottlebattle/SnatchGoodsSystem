package com.snatch.rabbitmq;

import com.snatch.domain.User;
import com.snatch.redis.RedisService;
import com.snatch.service.GoodsService;
import com.snatch.service.OrderService;
import com.snatch.service.SnatchService;
import com.snatch.vo.GoodsDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
		RedisService redisService;
		
		@Autowired
		GoodsService goodsService;
		
		@Autowired
		OrderService orderService;
		
		@Autowired
		SnatchService snatchService;
		
		@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void receive(String message) {
			//收到消息后开始执行
			log.info("receive message:"+message);
			SnatchMessage mm  =(SnatchMessage) RedisService.stringToBean(message, SnatchMessage.class);
			User user = mm.getUser();
			log.info("id:"+ user.getId());
			GoodsDetailVo goods = mm.getGoodsDetail();

	    	//减库存 下订单 写入秒杀订单
			snatchService.snatchgoods(user,goods);
		}
	
		@RabbitListener(queues=MQConfig.QUEUE)
		public void receivetest(String message) {
			log.info("receive message:"+message);
		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//		public void receiveTopic1(String message) {
//			log.info(" topic  queue1 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//		public void receiveTopic2(String message) {
//			log.info(" topic  queue2 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//		
		
}
