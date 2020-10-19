package com.yeon.chimtooview.Service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.yeon.chimtooview.Entity.Playlist;
import com.yeon.chimtooview.Entity.QVideo;
import com.yeon.chimtooview.Entity.Video;
import com.yeon.chimtooview.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService extends QuerydslRepositorySupport {
    public VideoService() { super(Video.class); }

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getVideoAll() {
        return videoRepository.findAll();
    }

    // 요약 화면의 TopFive 를 구성하기 위해 DB에서 Sort하여 Data를 가져오는 Service
    public List<List<Video>> getVideoAllForTopFive() {
        /**
         *   VIEW_COUNT: 0,
         *   LIKE_COUNT: 1,
         *   DISLIKE_COUNT: 2,
         *   LIKE_RATE: 3,
         *   LIKE_GAP: 4,
         *   VIEW_LIKE_RATE: 5,
         *   VIEW_LIKE_GAP: 6,
         *   DATE: 7,
         */
        QVideo qVideo = QVideo.video;

        List<List<Video>> topFiveVideoList = new ArrayList<>();

        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.dislikeCount.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeRate.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.likeGap.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewLikeRate.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.viewLikeGap.desc()).limit(5).fetch());
        topFiveVideoList.add(from(qVideo).orderBy(qVideo.publishedAt.desc()).limit(5).fetch());

        return topFiveVideoList;
    }

    public List<Video> getVideoAllByPlaylist(Playlist playlist) {
        return videoRepository.findAllByPlaylist(playlist);
    }

    public long getVideoAllCountByFilter(String searchWord, String searchWordPlaylist) {
        QVideo qVideo = QVideo.video;

        JPQLQuery<Video> query = from(qVideo);

        if(!searchWord.equals("__BLANK")) {
            query = query.where(qVideo.title.contains(searchWord));
        }

        if(!searchWordPlaylist.equals("__BLANK")) {
            query = query.where(qVideo.playlist.title.contains(searchWordPlaylist));
        }

        long count = query.fetchCount();

        return count;
    }

    public QueryResults<Video> getVideoAllOrderByPaging(int category, int order, String searchWord, String searchWordPlaylist, int pageCurrent) {
        QVideo qVideo = QVideo.video;
        OrderSpecifier orderSpecifier = null;

        if(category >= 0 && category <= 6) {
            NumberPath numberPath = null;

            switch (category) {
                case 0: // 조회수
                    numberPath = qVideo.viewCount;
                    break;
                case 1: // 좋아요
                    numberPath = qVideo.likeCount;
                    break;
                case 2: // 싫어요
                    numberPath = qVideo.dislikeCount;
                    break;
                case 3: // 좋싫비
                    numberPath = qVideo.likeRate;
                    break;
                case 4: // 좋싫차
                    numberPath = qVideo.likeGap;
                    break;
                case 5: // 조좋비
                    numberPath = qVideo.viewLikeRate;
                    break;
                case 6: // 조좋차
                    numberPath = qVideo.viewLikeGap;
                    break;
            }

            orderSpecifier = (order == 0) ? numberPath.asc() : numberPath.desc();
        } else if(category == 7) {  // 날짜
            orderSpecifier = (order == 0) ? qVideo.publishedAt.asc() : qVideo.publishedAt.desc();
        }

        JPQLQuery<Video> query = from(qVideo).orderBy(orderSpecifier).offset(50 * (pageCurrent-1)).limit(50);

        // 검색어가 __BLANK 가 아닐 경우의 처리 (검색어가 제대로 입력되었을 때)
        if(!searchWord.equals("__BLANK")) {
            query = query.where(qVideo.title.contains(searchWord));
        }

        if(!searchWordPlaylist.equals("__BLANK")) {
            query = query.where(qVideo.playlist.title.contains(searchWordPlaylist));
        }

        QueryResults<Video> queryResults = query.fetchResults();

        return queryResults;
    }

    public List<Video> getVideoOrderByViewCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.viewCount.desc()).limit(10).fetch();

        return videoList;
    }

    public List<Video> getVideoOrderByLikeCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.likeCount.desc()).limit(10).fetch();

        return videoList;
    }

    public List<Video> getVideoOrderByDislikeCountLimit10() {
        QVideo qVideo = QVideo.video;

        List<Video> videoList = from(qVideo).orderBy(qVideo.dislikeCount.desc()).limit(10).fetch();

        return videoList;
    }

    public Video postVideo(Video video) {
        Video videoFromDb = videoRepository.findById(video.getId()).orElse(null);

        Video result = null;

        if(videoFromDb == null) {
            result = videoRepository.save(video);
        } else {
            result = videoFromDb;
        }

        return result;
    }
}
