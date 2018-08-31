# 多线程
* [多线程](#%E5%A4%9A%E7%BA%BF%E7%A8%8B)
    * [一、多线程入门](#%E4%B8%80%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%85%A5%E9%97%A8)
      * [1、创建线程的五种方式](#1%E5%88%9B%E5%BB%BA%E7%BA%BF%E7%A8%8B%E7%9A%84%E4%BA%94%E7%A7%8D%E6%96%B9%E5%BC%8F)
      * [2、多线程基础知识](#2%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86)
    * [二、多线程之间实现同步](#%E4%BA%8C%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B9%8B%E9%97%B4%E5%AE%9E%E7%8E%B0%E5%90%8C%E6%AD%A5)
      * [1、线程安全问题](#1%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E9%97%AE%E9%A2%98)
      * [2、线程三大特性](#2%E7%BA%BF%E7%A8%8B%E4%B8%89%E5%A4%A7%E7%89%B9%E6%80%A7)
      * [3、线程同步](#3%E7%BA%BF%E7%A8%8B%E5%90%8C%E6%AD%A5)
      * [4、Java内存模型](#4java%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B)
    * [三、多线程间通讯](#%E4%B8%89%E5%A4%9A%E7%BA%BF%E7%A8%8B%E9%97%B4%E9%80%9A%E8%AE%AF)
    * [四、java并发包与并发队列](#%E5%9B%9Bjava%E5%B9%B6%E5%8F%91%E5%8C%85%E4%B8%8E%E5%B9%B6%E5%8F%91%E9%98%9F%E5%88%97)
      * [1、同步容器类](#1%E5%90%8C%E6%AD%A5%E5%AE%B9%E5%99%A8%E7%B1%BB)
      * [2、并发容器](#2%E5%B9%B6%E5%8F%91%E5%AE%B9%E5%99%A8)
    * [五、线程池原理剖析与锁的深度化](#%E4%BA%94%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%8E%9F%E7%90%86%E5%89%96%E6%9E%90%E4%B8%8E%E9%94%81%E7%9A%84%E6%B7%B1%E5%BA%A6%E5%8C%96)
      * [1、线程池的好处](#1%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E5%A5%BD%E5%A4%84)
      * [2、线程池的原理](#2%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E5%8E%9F%E7%90%86)
      * [3、线程池四种创建方式](#3%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%9B%9B%E7%A7%8D%E5%88%9B%E5%BB%BA%E6%96%B9%E5%BC%8F)
      
### 一、多线程入门
#### 1、创建线程的五种方式
    （1）使用继承Thread类方式[(thread_demo01)ThreadDemo01.java]
---
    （2）使用实现Runnable接口方式[(thread_demo01)ThreadDemo02.java]
---
    （3）使用匿名内部类方式[(thread_demo01)ThreadDemo03.java]
---
    （4）callable
---
    （5）使用线程池创建线程
#### 2、多线程基础知识
    (1)线程与进程区别
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo01/image/%E7%BA%BF%E7%A8%8B%E4%B8%8E%E8%BF%9B%E7%A8%8B%E5%8C%BA%E5%88%AB.png "在这里输入图片标题")

---
    (2)启动线程，不是调用run方法，而是调用start方法
---
    (3)多线程几种状态
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo01/image/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%87%A0%E7%A7%8D%E7%8A%B6%E6%80%81.png "在这里输入图片标题")

---
    (4)异步与同步区别
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo01/image/%E5%BC%82%E6%AD%A5%E4%B8%8E%E5%90%8C%E6%AD%A5%E5%8C%BA%E5%88%AB.png "在这里输入图片标题")

---
    (5)守护线程与非守护线程
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo01/image/%E5%AE%88%E6%8A%A4%E7%BA%BF%E7%A8%8B%E4%B8%8E%E9%9D%9E%E5%AE%88%E6%8A%A4%E7%BA%BF%E7%A8%8B.png "在这里输入图片标题")

---
    (6)多线程分批处理信息
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo01/image/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%88%86%E6%89%B9%E5%A4%84%E7%90%86%E4%BF%A1%E6%81%AF.png "在这里输入图片标题")

---
    (7)join()方法作用：thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。

### 二、多线程之间实现同步
#### 1、线程安全问题
    （1）线程安全问题
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E9%97%AE%E9%A2%98.png "在这里输入图片标题")

    （2）多线程安全问题产生
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E9%97%AE%E9%A2%98%E4%BA%A7%E7%94%9F.png "在这里输入图片标题")

    （3）多线程冲突产生
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%86%B2%E7%AA%81%E4%BA%A7%E7%94%9F.png "在这里输入图片标题")
    
    （4）线程安全问题解决办法
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E9%97%AE%E9%A2%98%E8%A7%A3%E5%86%B3%E5%8A%9E%E6%B3%95.png "在这里输入图片标题")
#### 2、线程三大特性
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E7%BA%BF%E7%A8%8B%E4%B8%89%E5%A4%A7%E7%89%B9%E6%80%A7.png "在这里输入图片标题")
#### 3、线程同步
    （1）多线程如何实现同步
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0%E5%90%8C%E6%AD%A5.png "在这里输入图片标题")

    （2）线程之间同步
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E7%BA%BF%E7%A8%8B%E4%B9%8B%E9%97%B4%E5%90%8C%E6%AD%A5.png "在这里输入图片标题")

    （3）同步函数与静态函数区别
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/%E5%90%8C%E6%AD%A5%E5%87%BD%E6%95%B0%E4%B8%8E%E9%9D%99%E6%80%81%E5%87%BD%E6%95%B0%E5%8C%BA%E5%88%AB.png "在这里输入图片标题")
#### 4、Java内存模型
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo02/image/java%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B.png "在这里输入图片标题")

### 三、多线程间通讯
    （1）生产者与消费者
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/%E7%94%9F%E4%BA%A7%E8%80%85%E4%B8%8E%E6%B6%88%E8%B4%B9%E8%80%85.png "在这里输入图片标题")

    （2）生成者与消费者通讯
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/%E7%94%9F%E6%88%90%E8%80%85%E4%B8%8E%E6%B6%88%E8%B4%B9%E8%80%85%E9%80%9A%E8%AE%AF.png "在这里输入图片标题")
    
    （3）lock与syn区别
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/lock%E4%B8%8Esyn%E5%8C%BA%E5%88%AB.png "在这里输入图片标题")

    （4）wait与sleep区别
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/wait%E4%B8%8Esleep%E5%8C%BA%E5%88%AB.png "在这里输入图片标题")

    （5）ThreadLock原理剖析
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/ThreadLock%E5%8E%9F%E7%90%86%E5%89%96%E6%9E%90.png "在这里输入图片标题")

    （6）怎么停止线程
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/%E6%80%8E%E4%B9%88%E5%81%9C%E6%AD%A2%E7%BA%BF%E7%A8%8B.png "在这里输入图片标题")

    （7）总结
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/%E6%80%BB%E7%BB%93.png "在这里输入图片标题")

### 四、java并发包与并发队列
#### 1、同步容器类
    1）Vector(低效集合)
---
    2）HasTable(低效集合)
---
    3）Collections.synchronized*(m) 将线程不安全额集合变为线程安全集合(低效集合) 
---
    4）ConcurrentHashMap(相对高效集合)
       使用分段锁，把一个整体分成16个段（最多16个段），每一个段都是一个HashTable。因此最多支持16个线程并发
---
    5）CountDownLatch利用它可以实现类似计数器的功能。
       比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行。
---
    6）CyclicBarrier 
       CyclicBarrier初始化时规定一个数目，然后计算调用了CyclicBarrier.await()进入等待的线程数。当线程数达到了这个数目时，
       所有进入等待状态的线程被唤醒并继续。
---
    7）Semaphore是一种基于计数的信号量。
       它可以设定一个阈值，基于此，多个线程竞争获取许可信号，做自己的申请后归还，超过阈值后，线程申请许可信号将ap会被阻塞。
#### 2、并发容器
    1）ConcurrentLinkedDeque（高性能队列）
        适用于高并发场景，无界线程安全队列，头是最先加入的，尾是最近加入的，该队列不允许null元素。
---
    2）BlockingQueue（阻塞队列，性能低于ConcurrentLinkedDeque）
---
        ① ArrayBlockingQueue：先进先出的存储方式，内部实现是数组，有边界，初始化时必须指定容量大小。
---
        ② LinkedBlockingQueue：先进先出的存储方式，内部实现是链表，阻塞队列的大小是可选的，如果初始化时指定了容量大小，就是有界；
          如果不指定，就是无界。
---
        ③ PriorityBlockingQueue：无边界，该队列允许null元素。所有插入PriorityBlockingQueue的对象必须实现 java.lang.Comparable
          接口，队列优先级的排序规则就是按照我们对这个接口的实现来定义的。另外，我们可以从PriorityBlockingQueue获得一个迭代器Iterator，
          但这个迭代器并不保证按照优先级顺序进行迭代。
---
        ④ SynchronousQueue：队列内部仅允许容纳一个元素。当一个线程插入一个元素后会被阻塞，除非这个元素被另一个线程消费。
    
### 五、线程池原理剖析与锁的深度化
#### 1、线程池的好处
    1）降低资源消耗
    2）提高响应速度
    3）提高线程的可管理行
![输入图片说明](https://github.com/mister-shen/javalearn/blob/master/multithread/thread_demo03/image/%E6%80%BB%E7%BB%93.png "在这里输入图片标题")
    
    线程池的作用：为突然大量爆发的线程而设计的，通过有限的几个固定线程为大量的操作服务，减少了创建和销毁线程所需的时间，从而提高效率
#### 2、线程池的原理
       Executor框架的最顶层实现是ThreadPoolExecutor类，Executors工厂类中提供的newScheduledThreadPool、newFixedThreadPool、
    newCachedThreadPool方法其实也只是ThreadPoolExecutor的构造函数参数不同而已。
       1）corePoolSize： 核心池的大小。 当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，
         就会把到达的任务放到缓存队列当中
       2）maximumPoolSize： 线程池最大线程数，它表示在线程池中最多能创建多少个线程；
       3）keepAliveTime： 表示线程没有任务执行时最多保持多久时间会终止。
       4）unit： 参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性
#### 3、线程池四种创建方式
    1）newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
---
    2）newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
---
    3）newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
---
    4）newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
